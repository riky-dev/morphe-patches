package app.riky.patches.capcut

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_CAPCUT

@Suppress("unused")
val securityNoticePatch = bytecodePatch(
    name = "Remove security notice",
    description = "Suppresses the modified-build security notice by disabling the risk SDK dialog entry point and the isCracking flag writer.",
    default = true
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        // Make CrackingInterceptor.c(int) a no-op so the "isCracking" flag is
        // never persisted.
        CrackingWriteFingerprint.method.addInstructions(
            0,
            """
                return-void
            """
        )

        // Make RiskControlService.execute short-circuit to "true" so the
        // VerifyDialog (WebView rendering the server's security notice) is
        // never launched.
        RiskControlExecuteFingerprint.method.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """
        )

        // Suppress the Lynx "app not secure" modal: if WebActivity is launched
        // with the crack-guide channel, finish() immediately so the modal never
        // renders. Normal web pages are unaffected (channel check is specific).
        // Since we are inserting at offset 0, we MUST call the super.onCreate first,
        // or let the original execution call super.onCreate. To avoid SuperNotCalledException
        // when we finish and return early, we call super.onCreate(p1) before finish().
        WebActivityOnCreateFingerprint.method.addInstructions(
            0,
            """
                invoke-virtual/range {p0}, Landroid/app/Activity;->getIntent()Landroid/content/Intent;
                move-result-object v0
                invoke-virtual {v0}, Landroid/content/Intent;->getData()Landroid/net/Uri;
                move-result-object v0
                if-eqz v0, :cc_skip
                invoke-virtual {v0}, Landroid/net/Uri;->toString()Ljava/lang/String;
                move-result-object v0
                const-string v1, "image_lynx_global_unsafe_pkg_modal"
                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
                move-result v0
                if-eqz v0, :cc_skip
                invoke-super/range {p0..p1}, Lcom/vega/web/WebBaseActivity;->onCreate(Landroid/os/Bundle;)V
                invoke-virtual/range {p0}, Lcom/vega/web/WebActivity;->finish()V
                return-void
                :cc_skip
            """
        )

        // Same modal hosted by the generic LynxActivity (covers TransLynxActivity).
        LynxActivityOnCreateFingerprint.method.addInstructions(
            0,
            """
                invoke-virtual/range {p0}, Landroid/app/Activity;->getIntent()Landroid/content/Intent;
                move-result-object v0
                invoke-virtual {v0}, Landroid/content/Intent;->getData()Landroid/net/Uri;
                move-result-object v0
                if-eqz v0, :cc_skip
                invoke-virtual {v0}, Landroid/net/Uri;->toString()Ljava/lang/String;
                move-result-object v0
                const-string v1, "image_lynx_global_unsafe_pkg_modal"
                invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z
                move-result v0
                if-eqz v0, :cc_skip
                invoke-super/range {p0..p1}, Landroidx/fragment/app/FragmentActivity;->onCreate(Landroid/os/Bundle;)V
                invoke-virtual/range {p0}, Lcom/vega/main/LynxActivity;->finish()V
                return-void
                :cc_skip
            """
        )
    }
}
