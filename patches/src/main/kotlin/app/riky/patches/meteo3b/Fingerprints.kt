package app.riky.patches.meteo3b

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object EvaluateProviderFingerprint : Fingerprint(
    definingClass = "Lcom/Meteosolutions/Meteo3b/manager/adv/BannerManager;",
    name = "evaluateProvider",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "Ljava/lang/String;",
    parameters = listOf("Lcom/Meteosolutions/Meteo3b/manager/adv/BannerManager\$BANNER_TYPE;")
)

internal object EvaluateFallbackFingerprint : Fingerprint(
    definingClass = "Lcom/Meteosolutions/Meteo3b/manager/adv/BannerManager;",
    name = "evaluateFallback",
    accessFlags = listOf(AccessFlags.PRIVATE),
    returnType = "Ljava/lang/String;",
    parameters = listOf()
)

internal object IsPremiumFingerprint : Fingerprint(
    definingClass = "Lcom/Meteosolutions/Meteo3b/data/models/User;",
    name = "isPremium",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "Z",
    parameters = listOf()
)

internal object IsConsentlessPremiumFingerprint : Fingerprint(
    definingClass = "Lcom/Meteosolutions/Meteo3b/data/models/User;",
    name = "isConsentlessPremium",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "Z",
    parameters = listOf()
)

internal object IsInterstitialAllowedFingerprint : Fingerprint(
    definingClass = "Lcom/Meteosolutions/Meteo3b/manager/adv/BannerManager;",
    name = "isInterstitialAllowed",
    accessFlags = listOf(AccessFlags.PUBLIC),
    returnType = "Z",
    parameters = listOf()
)

internal object HistoricalViewModelFingerprint : Fingerprint(
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    parameters = listOf(
        "Lcom/Meteosolutions/Meteo3b/data/repositories/HistoricalRepository;",
        "Lcom/Meteosolutions/Meteo3b/data/repositories/JwtRepository;",
        "Landroid/content/Context;"
    )
)

internal object HistoricalViewModelGetUserTypeFingerprint : Fingerprint(
    classFingerprint = HistoricalViewModelFingerprint,
    strings = listOf("[HistoricalViewModel - getUserType: "),
    filters = listOf(
        app.morphe.patcher.methodCall("Lcom/Meteosolutions/Meteo3b/data/models/User;->isPremium()Z")
    )
)
