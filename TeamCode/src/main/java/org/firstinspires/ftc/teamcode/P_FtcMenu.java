package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

public class P_FtcMenu
{
    private static final String moduleName = "FtcMenu";

    private static final long LOOP_INTERVAL     = 50;

    private static final int MENUBUTTON_BACK    = (1 << 0);
    private static final int MENUBUTTON_ENTER   = (1 << 1);
    private static final int MENUBUTTON_UP      = (1 << 2);
    private static final int MENUBUTTON_DOWN    = (1 << 3);

    private static int prevButtonStates = 0;

    private P_HalDashboard dashboard;
    private String menuTitle;
    private P_FtcMenu parent;
    private MenuButtons menuButtons;
    private ArrayList<String> choiceTextTable = new ArrayList<String>();
    private ArrayList<Object> choiceObjectTable = new ArrayList<Object>();
    private ArrayList<P_FtcMenu> childMenuTable = new ArrayList<P_FtcMenu>();
    private int currentChoice = -1;
    private int firstDisplayedChoice = 0;

    public interface MenuButtons
    {
        public boolean isMenuUpButton();
        public boolean isMenuDownButton();
        public boolean isMenuEnterButton();
        public boolean isMenuBackButton();
    }   //interface MenuButtons

    /**
     * Constructor: Creates an instance of the object.
     *
     * @param menuTitle specifies the title of the menu. The title will be displayed
     *                  as the first line in the menu.
     * @param parent specifies the parent menu to go back to if the BACK button
     *               is pressed. If this is the root menu, it can be set to null.
     * @param menuButtons specifies the object that implements the MenuButtons interface.
     */
    public P_FtcMenu(String menuTitle, P_FtcMenu parent, MenuButtons menuButtons)
    {
        if (menuButtons == null || menuTitle == null)
        {
            throw new NullPointerException("menuTitle/menuButtons must be provided");
        }

        dashboard = P_HalDashboard.getInstance();
        this.menuTitle = menuTitle;
        this.parent = parent;
        this.menuButtons = menuButtons;
    }   //FtcMenu

    /**
     * This method adds a choice to the menu. The choices will be displayed in the
     * order of them being added.
     *
     * @param choiceText specifies the choice text that will be displayed on the dashboard.
     * @param choiceObj specifies the object to be returned if the choice is selected.
     * @param childMenu specifies the next menu to go to when this choice is selected.
     *                  If this is the last menu (a leaf node in the tree), it can be set
     *                  to null.
     */
    public void addChoice(String choiceText, Object choiceObj, P_FtcMenu childMenu)
    {
        //final String funcName = "addChoice";

        choiceTextTable.add(choiceText);
        choiceObjectTable.add(choiceObj);
        childMenuTable.add(childMenu);
        if (currentChoice == -1)
        {
            //
            // This is the first added choice in the menu.
            // Make it the default choice by highlighting it.
            //
            currentChoice = 0;
        }
    }   //addChoice

    public void addChoice(String choiceText, Object choiceObj)
    {
        addChoice(choiceText, choiceObj, null);
    }   //addChoice

    /**
     * This method returns the parent menu of this menu.
     *
     * @return parent menu (can be null if this menu is the root menu).
     */
    public P_FtcMenu getParentMenu()
    {
        //final String funcName = "getParentMenu";

        return parent;
    }   //getParentMenu

    /**
     * This method returns the title text of this menu.
     *
     * @return title text.
     */
    public String getTitle()
    {
        final String funcName = "getTitle";

        return menuTitle;
    }   //getTitle

    /**
     * This method returns the choice text of the given choice index.
     *
     * @param choice specifies the choice index in the menu.
     * @return text of the choice if choice index is valid, null otherwise.
     */
    public String getChoiceText(int choice)
    {
        final String funcName = "getChoiceText";
        String text = null;
        int tableSize = choiceTextTable.size();

        if (tableSize > 0 && choice >= 0 && choice < tableSize)
        {
            text = choiceTextTable.get(choice);
        }

        return text;
    }   //getChoiceText

    /**
     * This method returns the choice object of the given choice index.
     *
     * @param choice specifies the choice index in the menu.
     * @return object of the given choice if choice index is valid, null otherwise.
     */
    public Object getChoiceObject(int choice)
    {
        final String funcName = "getChoiceObject";
        Object obj = null;
        int tableSize = choiceObjectTable.size();

        if (tableSize > 0 && choice >= 0 && choice < tableSize)
        {
            obj = choiceObjectTable.get(choice);
        }

        return obj;
    }   //getChoiceObject

    /**
     * This method returns the index of the current choice. Every menu has a
     * current choice even if the menu hasn't been displayed and the user
     * hasn't picked a choice. In that case, the current choice is the
     * highlighted selection of the menu which is the first choice in the menu.
     * If the menu is empty, the current choice index is -1.
     *
     * @return current choice index, -1 if menu is empty.
     */
    public int getCurrentChoice()
    {
        //final String funcName = "getCurrentChoice";

        return currentChoice;
    }   //getCurrentChoice

    /**
     * This method returns the text of the current choice. Every menu has a
     * current choice even if the menu hasn't been displayed and the user
     * hasn't picked a choice. In that case, the current choice is the
     * highlighted selection of the menu which is the first choice in the menu.
     * If the menu is empty, the current choice index is -1.
     *
     * @return current choice text, null if menu is empty.
     */
    public String getCurrentChoiceText()
    {
        return getChoiceText(currentChoice);
    }   //getCurrentChoiceText

    /**
     * This method returns the object of the current choice. Every menu has a
     * current choice even if the menu hasn't been displayed and the user
     * hasn't picked a choice. In that case, the current choice is the
     * highlighted selection of the menu which is the first choice in the menu.
     * If the menu is empty, the current choice index is -1.
     *
     * @return current choice object, null if menu is empty.
     */
    public Object getCurrentChoiceObject()
    {
        return getChoiceObject(currentChoice);
    }   //getCurrentChoiceObject

    /**
     * This method displays the menu and waits for the user to navigate the selections
     * and make a choice.
     * Note: this is a blocking method, it won't return until a choice is made or the
     * menu is canceled.
     *
     * @return choice index of the selection, -1 if the menu is canceled.
     */
    public int getUserChoice()
    {
        final String funcName = "getUserChoice";
        int choice = -1;
        boolean done = false;

        while (!done)
        {
            int currButtonStates = getMenuButtons();
            int changedButtons = currButtonStates ^ prevButtonStates;

            //
            // Check if any menu buttons changed states.
            //
            if (changedButtons != 0)
            {
                int buttonsPressed = currButtonStates & changedButtons;

                if ((buttonsPressed & MENUBUTTON_BACK) != 0)
                {
                    //
                    // MenuCancel is pressed. Set choice to none and exit.
                    //
                    choice = -1;
                    done = true;
                }
                else if ((buttonsPressed & MENUBUTTON_ENTER) != 0)
                {
                    //
                    // MenuEnter is pressed. Set choice to the selected choice and exit.
                    //
                    choice = currentChoice;
                    done = true;
                }
                else if ((buttonsPressed & MENUBUTTON_UP) != 0)
                {
                    //
                    // MenuUp is pressed. Move the selected choice up one.
                    //
                    prevChoice();
                }
                else if ((buttonsPressed & MENUBUTTON_DOWN)!= 0)
                {
                    //
                    // MenuDown is pressed. Move the selected choice down one.
                    //
                    nextChoice();
                }

                prevButtonStates = currButtonStates;
            }
            //
            // Refresh the display to show the choice movement.
            //
            displayMenu();
            sleep(LOOP_INTERVAL);
        }

        return choice;
    }   //getUserChoice

    public static void sleep(long sleepTime)
    {
        long wakeupTime = System.currentTimeMillis() + sleepTime;

        while (sleepTime > 0)
        {
            try
            {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
            }
            sleepTime = wakeupTime - System.currentTimeMillis();
        }
    }   //sleep

    public String getUserChoiceText()
    {
        return getChoiceText(getUserChoice());
    }   //getUserChoiceText

    /**
     * This method displays the menu and waits for the user to navigate the selections
     * and make a choice.
     * Note: this is a blocking method, it won't return until a choice is made or the
     * menu is canceled.
     *
     * @return choice object of the selection, null if the menu is canceled.
     */
    public Object getUserChoiceObject()
    {
        return getChoiceObject(getUserChoice());
    }   //getUserChoiceObject

    public static void walkMenuTree(P_FtcMenu rootMenu)
    {
        P_FtcMenu menu = rootMenu;

        while (menu != null && !Thread.currentThread().isInterrupted())
        {
            int choice = menu.getUserChoice();
            if (choice != -1)
            {
                //
                // User selected a choice, let's go to the next menu.
                //
                menu = menu.childMenuTable.get(choice);
            }
            else if (menu != rootMenu)
            {
                //
                // User canceled a menu, let's go back to the parent menu
                // unless we are already at the root menu in which case
                // we stay in the root menu.
                //
                menu = menu.getParentMenu();
            }
        }
        //
        // We are done with the menus. Let's clear the dashboard.
        //
        P_HalDashboard.getInstance().clearDisplay();
    }   //walkMenuTree

    /**
     * This method checks all the menu button states and combine them into an integer,
     * one bit for each button.
     *
     * @return an integer representing the states of all the menu buttons.
     */
    private int getMenuButtons()
    {
        final String funcName = "getMenuButtons";

        int buttons = 0;

        if (menuButtons.isMenuBackButton()) buttons |= MENUBUTTON_BACK;
        if (menuButtons.isMenuEnterButton()) buttons |= MENUBUTTON_ENTER;
        if (menuButtons.isMenuUpButton()) buttons |= MENUBUTTON_UP;
        if (menuButtons.isMenuDownButton()) buttons |= MENUBUTTON_DOWN;

        return buttons;
    }   //getMenuButtons

    /**
     * This method displays the menu on the dashboard with the current
     * selection highlighted. The number of choices in the menu may
     * exceed the total number of lines on the dashboard. In that case,
     * it will only display all the choices that will fit on the
     * dashboard. If the user navigates to a choice outside of the
     * dashboard display, the choices will scroll up or down to bring
     * the new selection into the dashboard.
     */
    private void displayMenu()
    {
        final String funcName = "displayMenu";

        //
        // Determine the choice of the last display line on the dashboard.
        //
        int lastDisplayedChoice = Math.min( firstDisplayedChoice + P_HalDashboard.MAX_NUM_TEXTLINES - 2,
                                            choiceTextTable.size() - 1);
        dashboard.clearDisplay();
        dashboard.displayPrintf(0, menuTitle);
        //
        // Display all the choices that will fit on the dashboard.
        //
        for (int i = firstDisplayedChoice; i <= lastDisplayedChoice; i++)
        {
           dashboard.displayPrintf(
                    i - firstDisplayedChoice + 1,
                    i == currentChoice? ">>\t%s": "%s", choiceTextTable.get(i));
        }
    }   //displayMenu

    /**
     * This method moves the current selection to the next choice in the menu.
     * If it is already the last choice, it will wraparound back to the first choice.
     */
    private void nextChoice()
    {
        final String funcName = "nextChoice";

        if (choiceTextTable.size() == 0)
        {
            currentChoice = -1;
        }
        else
        {
            currentChoice++;
            if (currentChoice >= choiceTextTable.size())
            {
                currentChoice = 0;
            }

            int lastDisplayedChoice =
                    Math.min(firstDisplayedChoice + P_HalDashboard.MAX_NUM_TEXTLINES - 2,
                            choiceTextTable.size() - 1);
            if (currentChoice > lastDisplayedChoice)
            {
                //
                // Scroll down.
                //
                firstDisplayedChoice = currentChoice - (P_HalDashboard.MAX_NUM_TEXTLINES - 2);
            }
        }


    }   //nextChoice

    /**
     * This method moves the current selection to the previous choice in the menu.
     * If it is already the first choice, it will wraparound back to the last choice.
     */
    private void prevChoice()
    {
        final String funcName = "prevChoice";

        if (choiceTextTable.size() == 0)
        {
            currentChoice = -1;
        }
        else
        {
            currentChoice--;
            if (currentChoice < 0)
            {
                currentChoice = choiceTextTable.size() - 1;
            }

            if (currentChoice < firstDisplayedChoice)
            {
                //
                // Scroll up.
                //
                firstDisplayedChoice = currentChoice;
            }
        }


    }   //prevChoice

}   //class FtcMenu