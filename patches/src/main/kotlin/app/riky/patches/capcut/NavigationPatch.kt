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
        // Resource IDs moved between CapCut 19.2.0 and 19.6.0. Try the 19.6.0
        // id first, fall back to 19.2.0, and skip ViewExtKt.d when both miss
        // (null would NPE on Kotlin non-null).
        BaseMainActivityInitMainTab6InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Lcom/vega/ui/BadgeButton;
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}6;->e:Lcom/vega/main/BaseMainActivity;

                const v2, 0x7f093930
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_tab6a
                const v2, 0x7f093824
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_tab6a
                :hide_tab6a
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_tab6a

                const v2, 0x7f093937
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_tab6b
                const v2, 0x7f09384c
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_tab6b
                :hide_tab6b
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_tab6b

                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )

        BaseMainActivityInitMainTab7InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Lcom/vega/ui/BadgeButton;
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}7;->e:Lcom/vega/main/BaseMainActivity;

                const v2, 0x7f093925
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_tab7
                const v2, 0x7f093842
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_tab7
                :hide_tab7
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_tab7

                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )

        BaseMainActivityInitMainTab10InvokeFingerprint.method.addInstructions(
            0,
            """
                const/4 v1, 0x0
                const-class v0, Lcom/vega/ui/BadgeButton;
                iget-object v3, p0, Lcom/vega/main/BaseMainActivity${'$'}initMainTab${'$'}10;->e:Lcom/vega/main/BaseMainActivity;

                const v2, 0x7f0938fb
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-nez v5, :hide_tab10
                const v2, 0x7f093818
                invoke-virtual {v3, v3, v2, v0}, Lcom/vega/ui/start/BaseInfraActivity;->findViewByIdCached(Lcom/kanyun/kace/AndroidExtensionsBase;ILjava/lang/Class;)Landroid/view/View;
                move-result-object v5
                if-eqz v5, :skip_tab10
                :hide_tab10
                invoke-static {v5, v1}, Lcom/vega/infrastructure/extensions/ViewExtKt;->d(Landroid/view/View;Z)V
                :skip_tab10

                sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
                return-object v0
            """
        )
    }
}
