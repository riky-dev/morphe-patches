package app.riky.patches.capcut

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_CAPCUT

@Suppress("unused")
val telemetrySuppressPatch = bytecodePatch(
    name = "Suppress Telemetry and Trackers",
    description = "Disables background monitoring and analytics frameworks including ByteDance Helios, AppLog, and AppsFlyer.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        // Suppress AppLog initialization and logging
        AppLogInitFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        AppLogOnEvent1Fingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        AppLogOnEvent2Fingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        AppLogOnEvent3Fingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        AppLogOnEvent4Fingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        AppLogOnEventV31Fingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        AppLogOnEventV32Fingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        AppLogOnEventV33Fingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        // Suppress Helios (ByteDance APM/Privacy monitoring) initialization
        HeliosEnvImplInitFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        // Suppress AppsFlyer startup
        AppsFlyerAnalyticsStartFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )
    }
}

@Suppress("unused")
val hideTemplatesTabPatch = bytecodePatch(
    name = "Hide Templates Tab",
    description = "Removes the second bottom navigation tab ('Templates' / 'Modelli') by forcing its visibility observer to always hide the tab.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        BaseMainActivityInitMainTab6InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Lcom/vega/ui/BadgeButton;
                const v2, 0x7f093824
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}6;->e:Lcom/vega/main/BaseMainActivity;
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                const v2, 0x7f09384c
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )
    }
}

@Suppress("unused")
val hideInboxTabPatch = bytecodePatch(
    name = "Hide Inbox Tab",
    description = "Removes the inbox / notifications bottom navigation tab by forcing its visibility observer to always hide the tab.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        BaseMainActivityInitMainTab7InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Lcom/vega/ui/BadgeButton;
                const v2, 0x7f093842
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}7;->e:Lcom/vega/main/BaseMainActivity;
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )
    }
}

@Suppress("unused")
val hideAiLabTabPatch = bytecodePatch(
    name = "Hide AI Lab Tab",
    description = "Removes the third bottom navigation tab ('AI Lab' / 'Lab. IA') by forcing its visibility observer to always hide the tab.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        // Force the observer callback to set visibility to GONE immediately and return
        BaseMainActivityInitMainTab10InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Lcom/vega/ui/BadgeButton;
                const v2, 0x7f093818
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}10;->e:Lcom/vega/main/BaseMainActivity;
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )
    }
}

@Suppress("unused")
val hideAiSoundTabPatch = bytecodePatch(
    name = "Hide AI Sounds Tab",
    description = "Disables the cloud-gated AI sounds category inside the sound-effects panel.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        AISoundEffectAbBFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )
    }
}

@Suppress("unused")
val hideHomeBotBannerPatch = bytecodePatch(
    name = "Hide Home Bottom Banner",
    description = "Suppresses the promotional bottom banner on the home screen (e.g. Twitch / subscription promos).",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        HomeBotBannerFragmentOnCreateViewFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return-object v0
            """
        )
    }
}

@Suppress("unused")
val hideHomeTopBannerPatch = bytecodePatch(
    name = "Hide Home Top Banner",
    description = "Suppresses the promotional header banner on the home screen.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        OverseaHomeTopBannerFragmentOnCreateViewFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return-object v0
            """
        )
    }
}

@Suppress("unused")
val hideSubscriptionUiPatch = bytecodePatch(
    name = "Hide Subscription UI",
    description = "Hides the home free-trial chip (SubscribeImpl.i) and blocks the ComposeSubscribeActivity paywall.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        VegaSubscribeImplIFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        ComposeSubscribeActivityOnCreateFingerprint.method.addInstructions(
            0,
            """
                invoke-super/range {p0..p1}, Landroidx/appcompat/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V
                invoke-virtual/range {p0}, Lcom/vega/subscription/widget/ComposeSubscribeActivity;->finish()V
                return-void
            """
        )
    }
}

@Suppress("unused")
val hideFalseHopesPatch = bytecodePatch(
    name = "Hide False Hopes Features",
    description = "Hides cloud-gated export options: super resolution, slow-motion optical flow, and export smart frame interpolation.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        // Hide IA ultra HD by disabling its AB setting config
        ExportHighDefinitionConfigGetEnableFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        // Hide Flusso ottico (Optical Flow) by returning only Frame Blending in slow motion source list
        SlowMotionSourceGetListFingerprint.method.addInstructions(
            0,
            """
                new-instance v4, Ljava/util/ArrayList;
                invoke-direct {v4}, Ljava/util/ArrayList;-><init>()V
                new-instance v3, Lcom/vega/edit/speed/data/SlowMotionItem;
                sget-object v2, Lcom/vega/middlebridge/swig/LVVEAlgorithmSubType;->c:Lcom/vega/middlebridge/swig/LVVEAlgorithmSubType;
                const v0, 0x7f121269
                invoke-static {v0}, Lcom/vega/infrastructure/base/ModuleCommonKt;->b(I)Ljava/lang/String;
                move-result-object v1
                const v0, 0x7f1230a1
                invoke-static {v0}, Lcom/vega/infrastructure/base/ModuleCommonKt;->b(I)Ljava/lang/String;
                move-result-object v0
                invoke-direct {v3, v2, v1, v0}, Lcom/vega/edit/speed/data/SlowMotionItem;-><init>(Lcom/vega/middlebridge/swig/LVVEAlgorithmSubType;Ljava/lang/String;Ljava/lang/String;)V
                invoke-virtual {v4, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
                return-object v4
            """
        )

        // Hide export smart frame interpolation entry (SmartCompleteFramePresenter gate)
        SmartCompleteFrameUtilIsEntryVisibleFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )
    }
}

@Suppress("unused")
val hideExportPromosPatch = bytecodePatch(
    name = "Hide Export Promos",
    description = "Suppresses post-export promotional carousel and export campaign join promos.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        BottomBannerHelperIsEnabledFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        BottomBannerHelperShowFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        ExportCampaignEnableConfigIsEnabledFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        PcGuideBannerOptEntranceAbIsEnabledFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        ResourcePositionAreaInitFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                invoke-static {p1, v0}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
            """
        )

        ResourcePositionAreaLoadResourceFingerprint.method.addInstructions(
            0,
            """
                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )

        ImageResourcePositionAreaLoadResourceFingerprint.method.addInstructions(
            0,
            """
                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )
    }
}

@Suppress("unused")
val hideHomeHeroPromosPatch = bytecodePatch(
    name = "Hide Home Hero Promos",
    description = "Disables server-driven home hero promo titles (e.g. Esprimi la tua vibe / Prova gli stili IA).",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        HomeBackgroundHelperIsEnabledFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )
    }
}

@Suppress("unused")
val hideProBadgesPatch = bytecodePatch(
    name = "Hide Pro Badges",
    description = "Suppresses Pro/SSVIP diamonds on BusinessMarkView (e.g. Riduci rumore in volume panel).",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        BusinessMarkViewRefreshFingerprint.method.addInstructions(
            0,
            """
                invoke-static {p0}, Lcom/vega/infrastructure/extensions/ViewExtKt;->b(Landroid/view/View;)V
                return-void
            """
        )
    }
}

