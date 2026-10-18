package app.riky.patches.meteo3b

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_METEO3B

@Suppress("unused")
val hideAdsPatch = bytecodePatch(
    name = "Hide ads",
    description = "Removes banner, native, cover and interstitial ads and unlocks premium features.",
    default = true
) {
    compatibleWith(COMPATIBILITY_METEO3B)

    execute {
        // Force user to be premium across the entire app.
        // This unlocks:
        // - 14-day forecasts in ForecastRepository
        // - Suppresses autopromo and promo cards
        // - Suppresses GDPR consent prompts
        IsPremiumFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )
        IsConsentlessPremiumFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )

        // Disable interstitials directly on BannerManager regardless of login state.
        IsInterstitialAllowedFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        // In HistoricalViewModel.getUserType(), check isLogged() instead of isPremium().
        // Since isPremium() is forced to true globally, HistoricalViewModel would otherwise
        // attempt to fetch hourly authenticated historical data without a JWT when logged out,
        // triggering a 401 error. By checking isLogged() here:
        // - Logged-out users get AnonymousUser (safe public daily averages, no 401 error).
        // - Logged-in users get PremiumUser (hourly historical data via their free session JWT).
        val isPremiumCallIndex = HistoricalViewModelGetUserTypeFingerprint.instructionMatches.first().index
        HistoricalViewModelGetUserTypeFingerprint.method.replaceInstruction(
            isPremiumCallIndex,
            "invoke-virtual {v0}, Lcom/Meteosolutions/Meteo3b/data/models/User;->isLogged()Z"
        )


        // Return "none" from the provider evaluation so every banner type
        // (native bottom, native top, sticky, cover, interstitial, fallback, ...)
        // resolves to an empty/invisible banner instead of a real ad provider.
        EvaluateProviderFingerprint.method.addInstructions(
            0,
            """
                const-string v0, "none"
                return-object v0
            """
        )

        // The fallback handlers dereference the banner returned by
        // initBannerStickyView/initBannerBottomView. Returning "no_adv" there
        // makes both handlers hide the banner instead of crashing on "none".
        EvaluateFallbackFingerprint.method.addInstructions(
            0,
            """
                const-string v0, "no_adv"
                return-object v0
            """
        )
    }
}