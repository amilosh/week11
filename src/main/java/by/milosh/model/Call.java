package by.milosh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Call {

    @Size(max = 30, message = "FirstName bust be less than 30 characters")
    private String firstName;

    @NotNull(message = "LastName is mandatory")
    @NotEmpty(message = "LastName should be not empty")
    @Size(max = 30, message = "FirstName bust be less than 30 characters")
    private String lastName;

    private LocalTime time;

    @NotNull(message = "LastName is mandatory")
    @NotEmpty(message = "LastName should be not empty")
    private String phoneNumber;
}
