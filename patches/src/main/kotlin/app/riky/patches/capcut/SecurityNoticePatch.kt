package app.riky.patches.capcut

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.riky.patches.shared.Constants.COMPATIBILITY_CAPCUT
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.builder.MutableMethodImplementation

@Suppress("unused")
val securityNoticePatch = bytecodePatch(
    name = "Remove Security Notice",
    description = "Suppresses modified-build security notices and geo-gated force-update " +
        "blocks: stubs the native CrackingInterceptor, risk SDK dialog entry, and " +
        "VersionUpdateService force popup. Server IP policy may still limit cloud assets.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        CrackingWriteFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        // Native intercept → plain pass-through (server ret codes can no longer kill the app).
        demoteNativeAndInject(
            CrackingInterceptFingerprint.method,
            registerCount = 3,
            """
                invoke-interface {p1}, Lcom/bytedance/retrofit2/intercept/Interceptor${'$'}Chain;->request()Lcom/bytedance/retrofit2/client/Request;
                move-result-object v0
                invoke-interface {p1, v0}, Lcom/bytedance/retrofit2/intercept/Interceptor${'$'}Chain;->proceed(Lcom/bytedance/retrofit2/client/Request;)Lcom/bytedance/retrofit2/SsResponse;
                move-result-object v0
                return-object v0
            """
        )

        demoteNativeAndInject(
            CrackingNativeDialogFingerprint.method,
            registerCount = 2,
            """
                return-void
            """
        )

        RetouchCrackingInterceptFingerprint.method.addInstructions(
            0,
            """
                invoke-interface {p1}, Lcom/bytedance/retrofit2/intercept/Interceptor${'$'}Chain;->request()Lcom/bytedance/retrofit2/client/Request;
                move-result-object v0
                invoke-interface {p1, v0}, Lcom/bytedance/retrofit2/intercept/Interceptor${'$'}Chain;->proceed(Lcom/bytedance/retrofit2/client/Request;)Lcom/bytedance/retrofit2/SsResponse;
                move-result-object v0
                return-object v0
            """
        )

        RiskControlExecuteFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )

        VersionUpdateShouldShowFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x0
                return v0
            """
        )

        VersionUpdateShowPopupFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )
    }
}

/**
 * Strip the NATIVE access flag and attach an empty mutable implementation so
 * [addInstructions] can rewrite methods that previously lived only in .so code.
 * Same approach as LemonSubscribeImpl.isVip in UnlockPremiumPatch.
 */
private fun demoteNativeAndInject(method: MutableMethod, registerCount: Int, instructions: String) {
    try {
        val currentFlags = method.accessFlags
        method.setAccessFlags(currentFlags and AccessFlags.NATIVE.value.inv())

        val field = method.javaClass.getDeclaredField("_implementation\$delegate")
        field.isAccessible = true
        field.set(method, lazyOf(MutableMethodImplementation(registerCount)))

        method.addInstructions(0, instructions)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
