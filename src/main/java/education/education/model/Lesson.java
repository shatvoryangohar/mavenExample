package education.education.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    private String name;
    private double duration;
    private String lecturerName;
    private double price;


}
