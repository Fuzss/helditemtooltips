package fuzs.helditemtooltips.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.helditemtooltips.HeldItemTooltips;
import fuzs.helditemtooltips.client.gui.screens.inventory.tooltip.HoverTextManager;
import fuzs.helditemtooltips.config.ClientConfig;
import fuzs.helditemtooltips.mixin.client.accessor.GuiAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SelectedItemHandler {
    public static final SelectedItemHandler INSTANCE = new SelectedItemHandler();

    private int remainingHighlightTicks;
    private ItemStack highlightingItemStack = ItemStack.EMPTY;
    private int maxLines;

    public void onClientTick$End(Minecraft minecraft) {

        if (minecraft.player == null || minecraft.isPaused()) return;

        ItemStack stack = minecraft.player.getInventory().getSelected();
        if (!this.highlightingItemStack.isEmpty() && stack.sameItem(this.highlightingItemStack) && stack.getHoverName().equals(this.highlightingItemStack.getHoverName())) {

            // item instance changes when using durability, to reflect this we need to update
            if (this.highlightingItemStack != stack) {

                this.highlightingItemStack = stack;
                HoverTextManager.reset();
            }

            if (this.remainingHighlightTicks > 0) {

                this.remainingHighlightTicks--;
            }
        } else {

            this.highlightingItemStack = stack;
            if (this.highlightingItemStack.isEmpty()) {

                this.remainingHighlightTicks = 0;
            } else {

                // get default vanilla value if not enabled
                this.remainingHighlightTicks = HeldItemTooltips.CONFIG.get(ClientConfig.class).displayTime;
                HoverTextManager.reset();
            }
        }
    }

    public void onRenderGuiOverlay$ItemName(PoseStack poseStack, int width, int height) {

        if (this.highlightingItemStack.isEmpty()) return;

        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getProfiler().push("selectedItemName");

        int alpha = HeldItemTooltips.CONFIG.get(ClientConfig.class).displayTime == 0 ? 255 : (int) Math.min(255.0F, (float) this.remainingHighlightTicks * 255.0F / 10.0F);

        if (alpha <= 0) return;

        final List<Component> lines = this.getTooltipLines(minecraft);
        final float currentScale = HeldItemTooltips.CONFIG.get(ClientConfig.class).displayScale / 6.0F;
        final int posX = this.getPosX(currentScale, width);
        int posY = this.getPosY(currentScale, height, lines.size(), minecraft);

        Font fontRenderer = minecraft.font;
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        poseStack.scale(currentScale, currentScale, 1.0F);

        this.drawBackground(poseStack, posX, posY, alpha, lines, minecraft);

        for (int i = 0; i < lines.size(); i++) {

            Component component = lines.get(i);
            GuiComponent.drawCenteredString(poseStack, fontRenderer, component, posX, posY, 0xFFFFFF + (alpha << 24));
            posY += i == 0 ? fontRenderer.lineHeight + 3 : fontRenderer.lineHeight + 1;
        }

        poseStack.scale(1.0F / currentScale, 1.0F / currentScale, 1.0F);
        RenderSystem.disableBlend();
        poseStack.popPose();

        minecraft.getProfiler().pop();
    }

    private int getPosX(float currentScale, int screenWidth) {

        int posX = (int) (screenWidth / (2.0F * currentScale));
        posX += HeldItemTooltips.CONFIG.get(ClientConfig.class).offsetX;

        return posX;
    }

    private int getPosY(float currentScale, int screenHeight, int allLines, Minecraft minecraft) {

        int posY = (int) (screenHeight / currentScale);

        posY -= HeldItemTooltips.CONFIG.get(ClientConfig.class).offsetY / currentScale;

        if (!minecraft.gameMode.canHurtPlayer()) {

            posY += 14;
        }

        posY -= allLines > 1 ? (allLines - 1) * 10 + 2 : (allLines - 1) * 10;
        return posY;
    }

    private List<Component> getTooltipLines(Minecraft minecraft) {

        ClientConfig clientConfig = HeldItemTooltips.CONFIG.get(ClientConfig.class);

        int maxLines;
        if (clientConfig.itemBlacklist.contains(this.highlightingItemStack.getItem())) {

            maxLines = 1;
        } else {

            int overlayMessageTime = ((GuiAccessor) minecraft.gui).helditemtooltips$getOverlayMessageTime();
            maxLines = overlayMessageTime > 0 ? (minecraft.gameMode.canHurtPlayer() ? 1 : 2) : clientConfig.maxLines;
        }

        if ((clientConfig.displayTime - this.remainingHighlightTicks) % clientConfig.updateInterval == 0 || this.maxLines != maxLines) {

            HoverTextManager.reset();
        }

        this.maxLines = maxLines;

        return HoverTextManager.getTooltipLines(this.highlightingItemStack, minecraft.player, this.maxLines);
    }

    private void drawBackground(PoseStack poseStack, int posX, int posY, int alpha, List<Component> lines, Minecraft minecraft) {

        ClientConfig.HoverTextBackground background = HeldItemTooltips.CONFIG.get(ClientConfig.class).background;

        Font font = minecraft.font;
        alpha = (int) (alpha * minecraft.options.textBackgroundOpacity().get());

        if (background == ClientConfig.HoverTextBackground.RECTANGLE) {

            int maximumWidth = lines.stream().mapToInt(font::width).max().orElse(0) / 2;
            int size = lines.size();

            GuiComponent.fill(poseStack, posX - maximumWidth - 2, posY - 2, posX + maximumWidth + 2, posY + size * (font.lineHeight + 1) + (size > 1 ? 1 : -1) + 2, alpha << 24);
        } else if (background == ClientConfig.HoverTextBackground.ADAPTIVE) {

            for (int i = 0; i < lines.size(); i++) {

                int previousWidth = textWidth(font, lines, i - 1) / 2;
                int currentWidth = textWidth(font, lines, i) / 2;
                int nextWidth = textWidth(font, lines, i + 1) / 2;
                int top = currentWidth < previousWidth ? (i == 1 ? 1 : -1) : 2;
                int bottom = currentWidth <= nextWidth ? (i == 0 ? 1 : -1) : 2;

                GuiComponent.fill(poseStack, posX - currentWidth - 2, posY - top, posX + currentWidth + 2, posY + font.lineHeight + bottom, alpha << 24);
                posY += i == 0 ? font.lineHeight + 3 : font.lineHeight + 1;
            }
        }
    }

    private static int textWidth(Font font, List<Component> lines, int index) {

        int clampedIndex = Mth.clamp(index, 0, lines.size() - 1);
        return clampedIndex == index ? font.width(lines.get(index)) : 0;
    }
}
