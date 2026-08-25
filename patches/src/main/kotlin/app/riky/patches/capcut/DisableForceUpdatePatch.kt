package app.riky.patches.capcut

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_CAPCUT

@Suppress("unused")
val disableForceUpdatePatch = bytecodePatch(
    name = "Disable force update",
    description = "Stops CapCut's remote force-update nag (same effect as going offline for " +
        "that warning) without cutting network access. No-ops VersionUpdateService fetch, " +
        "eligibility check, and splash dialog. Does not spoof connectivity — templates, " +
        "effects, and cloud still need the internet. Login-time app-upgrade errors are " +
        "separate and not covered here.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        // Splash checks this before calling showPopupDialog.
        VersionUpdateShouldShowFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """,
        )

        // Startup fetch of version_update config. A failed fetch clears the local cache
        // (why airplane mode hides the nag); skip the fetch entirely.
        VersionUpdateRefreshFingerprint.method.addInstructions(
            0,
            """
                return-void
            """,
        )

        VersionUpdateShowPopupFingerprint.method.addInstructions(
            0,
            """
                return-void
            """,
        )
    }
}
