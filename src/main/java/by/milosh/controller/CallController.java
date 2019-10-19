package by.milosh.controller;

import by.milosh.model.Call;
import by.milosh.service.SaveCallService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalTime;

@RestController
@RequestMapping(path = "/calls")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CallController {

    private SaveCallService saveCallService;

    @PutMapping(path = "/save")
    public String getNumber(@Valid @ModelAttribute Call call) throws IOException {
        call.setTime(LocalTime.now());
        saveCallService.save(call);
        return "The call was successfully saved";
    }
}
