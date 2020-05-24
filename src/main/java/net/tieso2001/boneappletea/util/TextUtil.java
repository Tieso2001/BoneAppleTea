package net.tieso2001.boneappletea.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static List<String> fluidTankContentList(FluidTank tank) {
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

    public static String fluidTankContentString(FluidTank tank) {
        FluidStack stack = tank.getFluid();
        int tankCapacity = tank.getCapacity();
        return fluidTankContentString(stack, tankCapacity);
    }

    public static String fluidTankContentString(FluidStack stack, int tankCapacity) {
        ITextComponent name = stack.getDisplayName();
        int amount = stack.getAmount();

        String string = reformatInt(amount) + " / " + reformatInt(tankCapacity) + " mB";
        if (!stack.isEmpty()) {
            string = name.getFormattedText() + ": " + string;
        }
        return string;
    }

    public static String reformatInt(int integer) {
        return String.format("%,d", integer);
    }
}
