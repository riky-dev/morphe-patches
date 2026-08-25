package app.riky.patches.capcut

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object VegaSubscribeIsVipFingerprint : Fingerprint(
    definingClass = "Lcom/vega/subscribe/SubscribeImpl;",
    name = "isVip",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf(),
)

internal object PayVipIsVipFingerprint : Fingerprint(
    definingClass = "Lcom/lemon/editor/proxy/PayVipImpl;",
    name = "isVip",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf(),
)

internal object UserInfoServiceVipStateFingerprint : Fingerprint(
    definingClass = "Lcom/lemon/clipmonetize/biz/userinfoapi/UserInfoServiceServiceImpl;",
    name = "f",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Lcom/lemon/lv/clipmonetize/data/VipState;",
    parameters = listOf(),
)

internal object SubscribeFacadeIsSubscribedFingerprint : Fingerprint(
    definingClass = "Lcom/xt/retouch/account/api/subscribe/SubscribeFacade;",
    name = "b",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(),
)

internal object WatermarkTrailerConfigInitFingerprint : Fingerprint(
    definingClass = "Lcom/lemon/lv/config/WatermarkTrailerConfig;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR),
    parameters = listOf("Z", "Z"),
)

internal object JniToastShowApkErrorFingerprint : Fingerprint(
    definingClass = "Lcom/xt/retouch/baseui/JniToast;",
    name = "showApkError",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "V",
    parameters = listOf("Landroid/content/Context;"),
)

internal object JniToastShowApkErrorRunnableFingerprint : Fingerprint(
    definingClass = "Lcom/xt/retouch/baseui/JniToast${'$'}1;",
    name = "run",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "V",
    parameters = listOf(),
)

/** Splash gate: true when cached remote update config applies to this OS. */
internal object VersionUpdateShouldShowFingerprint : Fingerprint(
    definingClass = "Lcom/vega/main/update/VersionUpdateService;",
    name = "a",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    returnType = "Z",
    parameters = listOf(),
)

/** Fetches / refreshes version_update remote popup config (needs network). */
internal object VersionUpdateRefreshFingerprint : Fingerprint(
    definingClass = "Lcom/vega/main/update/VersionUpdateService;",
    name = "c",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "V",
    parameters = listOf(),
)

/** Shows the force/suggested update dialog from splash. */
internal object VersionUpdateShowPopupFingerprint : Fingerprint(
    definingClass = "Lcom/vega/main/update/VersionUpdateService;",
    name = "e",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "V",
    parameters = listOf("Lcom/vega/ui/accomponent/AcComponentActivity;"),
)

// --- Connectivity spoof (NetworkUtils helpers) ---

internal object VegaNetworkIsConnectedFingerprint : Fingerprint(
    definingClass = "Lcom/vega/infrastructure/util/NetworkUtils;",
    name = "e",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(),
)

internal object VegaNetworkHasTransportFingerprint : Fingerprint(
    definingClass = "Lcom/vega/infrastructure/util/NetworkUtils;",
    name = "f",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(),
)

internal object VegaNetworkIsWifiFingerprint : Fingerprint(
    definingClass = "Lcom/vega/infrastructure/util/NetworkUtils;",
    name = "h",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(),
)

internal object VegaNetworkTypeFingerprint : Fingerprint(
    definingClass = "Lcom/vega/infrastructure/util/NetworkUtils;",
    name = "d",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Lcom/vega/infrastructure/util/NetworkUtils${'$'}NetworkType;",
    parameters = listOf(),
)

internal object RetouchNetworkIsConnectedFingerprint : Fingerprint(
    definingClass = "Lcom/xt/retouch/util/NetworkUtils;",
    name = "c",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(),
)

internal object RetouchNetworkIsWifiFingerprint : Fingerprint(
    definingClass = "Lcom/xt/retouch/util/NetworkUtils;",
    name = "d",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(),
)

internal object RetouchNetworkTypeFingerprint : Fingerprint(
    definingClass = "Lcom/xt/retouch/util/NetworkUtils;",
    name = "b",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Lcom/xt/retouch/util/NetworkUtils${'$'}NetworkType;",
    parameters = listOf(),
)

internal object BytedanceIsNetworkAvailableFingerprint : Fingerprint(
    definingClass = "Lcom/bytedance/common/utility/NetworkUtils;",
    name = "isNetworkAvailable",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

internal object BytedanceIsNetworkAvailableFastFingerprint : Fingerprint(
    definingClass = "Lcom/bytedance/common/utility/NetworkUtils;",
    name = "isNetworkAvailableFast",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

internal object BytedanceGetNetworkTypeFingerprint : Fingerprint(
    definingClass = "Lcom/bytedance/common/utility/NetworkUtils;",
    name = "getNetworkType",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Lcom/bytedance/common/utility/NetworkUtils${'$'}NetworkType;",
    parameters = listOf("Landroid/content/Context;"),
)

internal object BytedanceIsWifiFingerprint : Fingerprint(
    definingClass = "Lcom/bytedance/common/utility/NetworkUtils;",
    name = "isWifi",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

internal object BytedanceIsWifiFastFingerprint : Fingerprint(
    definingClass = "Lcom/bytedance/common/utility/NetworkUtils;",
    name = "isWifiFast",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

internal object LmNetworkAnyConnectedFingerprint : Fingerprint(
    definingClass = "Lcom/lm/components/utils/NetworkUtils;",
    name = "a",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

internal object LmNetworkIsAvailableFingerprint : Fingerprint(
    definingClass = "Lcom/lm/components/utils/NetworkUtils;",
    name = "b",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf("Landroid/content/Context;"),
)

internal object LmNetworkIsWifiFingerprint : Fingerprint(
    definingClass = "Lcom/lm/components/utils/NetworkUtils;",
    name = "c",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.STATIC),
    returnType = "Z",
    parameters = listOf(),
)
