package DateChooser;

import DTO.NgayThangNamDTO;



public interface EventDateChooser {

    public void dateSelected(SelectedAction action, NgayThangNamDTO date);
}