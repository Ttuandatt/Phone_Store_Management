/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.dashboardApp.raven.popup;

public interface PopupCallbackAction {

    public static final int CLOSE = -1;

    public void action(PopupController controller, int action);
}