package app.riky.patches.capcut

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.riky.patches.shared.Constants.COMPATIBILITY_CAPCUT

@Suppress("unused")
val simplifyNavigationPatch = bytecodePatch(
    name = "Simplify Navigation",
    description = "Removes the Templates, Inbox, and AI Lab bottom navigation tabs.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_CAPCUT)

    execute {
        // CapCut 19.6.0 ships multiple versionCodes with shifted R.id values
        // (apkpure vc19600100 vs Play/Manager vc19600002 are off-by-one).
        // Try each candidate; hide on first hit. Use View — BadgeButton.class
        // made findViewByIdCached return null for MeasureOnceRelativeLayout2 tabs.
        // Also hide subscribe chip + home hero (same Activity hook).
        BaseMainActivityInitMainTab6InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Landroid/view/View;
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}6;->e:Lcom/vega/main/BaseMainActivity;

                const v2, 0x7f093930
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_template
                const v2, 0x7f09392f
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_template
                const v2, 0x7f093824
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_template
                :hide_template
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_template

                const v2, 0x7f0916a8
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_subscribe
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_subscribe

                const v2, 0x7f091a97
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_home_bg
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_home_bg

                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )

        BaseMainActivityInitMainTab7InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Landroid/view/View;
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}7;->e:Lcom/vega/main/BaseMainActivity;

                const v2, 0x7f093925
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_message
                const v2, 0x7f093924
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_message
                const v2, 0x7f093842
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_message
                :hide_message
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_message

                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )

        BaseMainActivityInitMainTab10InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Landroid/view/View;
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}10;->e:Lcom/vega/main/BaseMainActivity;

                const v2, 0x7f0938fb
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_ailab
                const v2, 0x7f0938fa
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_ailab
                const v2, 0x7f093818
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_ailab
                :hide_ailab
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_ailab

                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )
    }
}
