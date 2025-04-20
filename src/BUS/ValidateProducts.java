package BUS;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ValidateProducts {
    private ArrayList<String> strings = new ArrayList<>();

    public void isRequired(String double1, String message) {
        if (double1 == null || double1.trim().isEmpty()) {
            strings.add(message + " không được để trống");
        }
    }
    public void isRequired(byte[] data, String message){
        if (data == null) {
            strings.add(message + " không được để trống");
        }
    }
    public void isRequired(Integer data, String message) {
        if (data == null || data <= 0) {
            strings.add(message + " không hợp lệ");
        }
    }
    public void isNumber(String data, String message){
        try {
            Integer.parseInt(data);
        } catch (Exception e) {
            strings.add(message + " phải là số!");
        }
    }

    public String toString() {
        String stringError = new String();
        for (String string : strings) {
            stringError += string + " \n";
        }
        return stringError;
    }

    public boolean showError() {
        boolean flag=false;
        if(!this.toString().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi nhập liệu");
            alert.setHeaderText(null);
            alert.setContentText(this.toString());
            alert.showAndWait();
            flag= true;
        }
        this.strings=new ArrayList<>();
        return flag;
    }
	

}
