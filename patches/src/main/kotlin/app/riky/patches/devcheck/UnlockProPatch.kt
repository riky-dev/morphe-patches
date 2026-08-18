package app.riky.patches.devcheck

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_DEVCHECK

// Unlocks DevCheck Pro without requiring an in-app purchase.
//
// The app is heavily R8-obfuscated (classes "wj", "wb", "sb1", ...) and uses
// Google Play Billing. Pro status funnels through two choke points:
//   - wj.d(): the synchronous "is Pro" boolean every paid feature gates on.
//   - wb.y(): the shared setter that records each product's purchase state
//     into a LiveData; the combined pro flow (and thus all reactive UI and the
//     paywall's auto-dismiss) is derived from those states.
@Suppress("unused")
val unlockProPatch = bytecodePatch(
    name = "Unlock Pro",
    description = "Unlocks all paid DevCheck Pro features (tests, tools, widgets, floating monitors, color schemes) without purchasing.",
    default = true
) {
    compatibleWith(COMPATIBILITY_DEVCHECK)

    execute {
        // wj.d() always answers "yes, this user is Pro", so every feature that
        // calls d() (tests, tools, color picker, monitors) is unlocked directly.
        IsProFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )

        // Pin the purchase state that wb.y() records to PURCHASED (sb1.o).
        // p2 is the sb1 parameter; forcing it to sb1.o makes each product
        // report as owned, which flips the combined pro LiveData to true. That
        // in turn hides the paywall, switches the app to "Pro" mode, and sets
        // the cached prefHardwareDB flag background/widgets rely on.
        SetPurchaseStateFingerprint.method.addInstructions(
            0,
            """
                sget-object p2, Lsb1;->o:Lsb1;
            """
        )
    }
}