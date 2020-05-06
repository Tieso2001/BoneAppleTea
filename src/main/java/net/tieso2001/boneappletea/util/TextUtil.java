package net.tieso2001.boneappletea.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static List<String> fluidTankContent(FluidTank tank) {
        List<String> stringList = new ArrayList<>();

        FluidStack stack = tank.getFluid();
        ITextComponent name = stack.getDisplayName();
        int amount = stack.getAmount();
        int capacity = tank.getCapacity();

        if (!stack.isEmpty()) {
            stringList.add(name.getFormattedText());
        }
        stringList.add(TextFormatting.GRAY + reformatInt(amount) + " / " + reformatInt(capacity) + " mB");
        return stringList;
    }

    public static String reformatInt(int integer) {
        return String.format("%,d", integer);
    }
}
