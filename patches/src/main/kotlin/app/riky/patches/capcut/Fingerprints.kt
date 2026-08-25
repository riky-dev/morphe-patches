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
