package fuzs.helditemtooltips.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TooltipComponentConfig implements ConfigCore {
    @Config(description = "Should this tooltip component be included when rendering held item tooltips.")
    public boolean include;
    @Config(description = "The order in which this tooltip component appears in relation to other components. Smaller values appear first.")
    public int ordering;
    @Config(description = "Priority for rendering this tooltip. When not enough lines are available (like an item with enchantments), components with a low priority will be skipped.")
    public int priority;
    @Config(description = "Represent information for this component as if advanced tooltips were enabled independently of the actual setting.")
    public boolean advancedTooltips;
    @Config(description = "Should this tooltip component be hidden when vanilla's respective \"HideFlags\" property is set.")
    public boolean respectHideFlags = true;
    @Config(description = "Text formatting settings for this component's text appearance.")
    final FormattingConfig formatting;

    public static TooltipComponentConfig simple(boolean include, int ordering, int priority) {
        return simple(include, ordering, priority, null);
    }

    public static TooltipComponentConfig simple(boolean include, int ordering, int priority, @Nullable ChatFormatting textColor) {
        return new TooltipComponentConfig(include, ordering, priority, false, new FormattingConfig(textColor));
    }

    public static TooltipComponentConfig advanced(boolean include, int ordering, int priority, @Nullable ChatFormatting textColor) {
        return new TooltipComponentConfig(include, ordering, priority, true, new FormattingConfig(textColor));
    }

    public TooltipComponentConfig(boolean include, int ordering, int priority, boolean advancedTooltips, @NotNull FormattingConfig formatting) {
        this.include = include;
        this.ordering = ordering;
        this.priority = priority;
        this.advancedTooltips = advancedTooltips;
        this.formatting = formatting;
    }

    public Style getStyle() {
        return FormattingConfig.toStyle(this.formatting);
    }

    static class FormattingConfig implements ConfigCore {
        private static final String DEFAULT_FORMATTING = "default";

        @Config(name = "text_color", description = "The color of this component's text.")
        @Config.AllowedValues(values = {DEFAULT_FORMATTING, "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray", "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white"})
        String textColorRaw;
        @Config(description = "Should the text in this component be replaced by random characters.")
        public boolean obfuscated;
        @Config(description = "Should the text in this component appear bold.")
        public boolean bold;
        @Config(description = "Should the text in this component appear struck-through.")
        public boolean strikethrough;
        @Config(description = "Should the text in this component appear with an underline.")
        public boolean underline;
        @Config(description = "Should the text in this component appear italic.")
        public boolean italic;

        @Nullable
        public ChatFormatting textColor;

        public FormattingConfig(@Nullable ChatFormatting textColor) {
            this(textColor, false, false, false, false, false);
        }

        public FormattingConfig(@Nullable ChatFormatting textColor, boolean obfuscated, boolean bold, boolean strikethrough, boolean underline, boolean italic) {
            this.textColorRaw = textColor == null ? DEFAULT_FORMATTING : textColor.getSerializedName();
            this.obfuscated = obfuscated;
            this.bold = bold;
            this.strikethrough = strikethrough;
            this.underline = underline;
            this.italic = italic;
        }

        @Override
        public void afterConfigReload() {
            this.textColor = ChatFormatting.getByName(this.textColorRaw);
        }

        public static Style toStyle(FormattingConfig formatting) {
            Style style = Style.EMPTY;
            if (formatting.textColor != null) style = style.withColor(formatting.textColor);
            if (formatting.obfuscated) style = style.withObfuscated(true);
            if (formatting.bold) style = style.withBold(true);
            if (formatting.strikethrough) style = style.withStrikethrough(true);
            if (formatting.underline) style = style.withUnderlined(true);
            if (formatting.italic) style = style.withItalic(true);
            return style;
        }
    }
}
