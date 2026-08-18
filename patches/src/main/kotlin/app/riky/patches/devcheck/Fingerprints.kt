package app.riky.patches.devcheck

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

// wj.d() — the central "is Pro" boolean used to gate every paid feature
// (tests, tools, color picker, widgets, floating monitors). Obfuscated to
// "wj" via R8; class and method resolved by name/return/params below.
internal object IsProFingerprint : Fingerprint(
    definingClass = "Lwj;",
    name = "d",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf()
)

// wb.y(String, sb1) — sets a product's purchase-state LiveData (ms0). Pinning
// the incoming state to PURCHASED (sb1.o) makes every product report as owned,
// which propagates to the combined pro flow and unlocks all reactive UI.
internal object SetPurchaseStateFingerprint : Fingerprint(
    definingClass = "Lwb;",
    name = "y",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "V",
    parameters = listOf("Ljava/lang/String;", "Lsb1;")
)