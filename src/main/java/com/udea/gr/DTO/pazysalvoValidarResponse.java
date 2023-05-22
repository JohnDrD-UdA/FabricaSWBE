package com.udea.gr.DTO;

import java.util.Optional;

public class pazysalvoValidarResponse {
    public Optional<studentDataResponse> studentData;
    public Optional<Boolean> materiasOb;
    public Optional<Boolean> materiasElec;
    public Optional<Boolean> pendientesNotas;
    public Optional<Boolean> biblioteca;
    public Optional<Boolean> cartera;
    public Optional<Boolean> impedimento;
    public String msg;

}
