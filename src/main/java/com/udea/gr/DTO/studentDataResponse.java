package com.udea.gr.DTO;

public class studentDataResponse {
    public String name;
    public String program;
    public String programCode;

    public studentDataResponse() {
    }

    public studentDataResponse(String Name, String Program, String ProgramCode) {
        this.name = Name;
        this.program = Program;
        this.programCode = ProgramCode;
    }

}