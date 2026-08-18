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

// PairIP (Google Play app protection) runs at startup and rejects re-signed APKs.
// verifyIntegrity() throws when the Morphe signature does not match the Play Store cert.
internal object PairIpVerifyIntegrityFingerprint : Fingerprint(
    definingClass = "Lcom/pairip/SignatureCheck;",
    name = "verifyIntegrity",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "V",
    parameters = listOf("Landroid/content/Context;")
)

// Startup VM bytecode (assets/DFQzzv4Rl5kZNZOE) opens Play Store on tampered installs.
// The program name lives in a static field, so fingerprint launch() by its invoke call.
internal object PairIpVmRunnerInvokeFingerprint : Fingerprint(
    definingClass = "Lcom/pairip/VMRunner;",
    name = "invoke",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Ljava/lang/Object;",
    parameters = listOf("Ljava/lang/String;", "[Ljava/lang/Object;")
)
