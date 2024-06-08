package kz.bitlab.trelloCom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_tasks")
public class Tasks extends BaseModel{
    private String title;

    @Column(columnDefinition = "text")
    private String description; // TEXT

    public enum TaskStatus{
        TODO(0),
        IN_TEST(1),
        DONE(2),
        FAILED(3);
        private final int value;

        TaskStatus(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
    }
    private TaskStatus status; // 0 - todo, 1 - intest, 2 - done, 3 - failed

    @ManyToOne
    Folders folder;
}
