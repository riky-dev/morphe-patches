package app.riky.patches.phototime

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

/**
 * PremiumStatus enum (`du6`): UNKNOWN / BASIC / PREMIUM.
 * `a()` returns true iff this == PREMIUM — used by feature gates.
 */
internal object PremiumStatusIsPremiumFingerprint : Fingerprint(
    definingClass = "Ldu6;",
    name = "a",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf(),
)

/**
 * Maps IS_PREMIUM_USER + supporter-code flags → PremiumStatus.
 * Forcing PREMIUM keeps PhototimeState.premiumStatus consistent with gates.
 */
internal object PremiumStatusFromFlagsFingerprint : Fingerprint(
    definingClass = "Lcca;",
    name = "P",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC, AccessFlags.FINAL),
    returnType = "Ldu6;",
    parameters = listOf("Ljava/lang/Boolean;", "Ljava/lang/Boolean;"),
)
