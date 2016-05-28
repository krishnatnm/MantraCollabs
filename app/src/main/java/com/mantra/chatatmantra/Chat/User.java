package com.mantra.chatatmantra.Chat;

/**
 * Created by rajat on 28/05/16.
 */
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    String id, name, email;
}
