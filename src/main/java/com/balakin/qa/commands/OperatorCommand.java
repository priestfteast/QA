package com.balakin.qa.commands;

import com.balakin.qa.domain.FireCause;
import com.balakin.qa.domain.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OperatorCommand {

        private Long id;

        @NotEmpty
        @Size(min = 2, max = 50)
        private String fullName;

        @javax.validation.constraints.NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @PastOrPresent
        private LocalDate employementDate;

        private boolean fired;

        @Nullable
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @PastOrPresent
        private LocalDate retirementDate;

        private FireCause fireCause;

        private Project project;

    }