package dto;

import java.io.Serializable;

public class LeituraDto implements Serializable {
    public int droneId;
    public double latitude;
    public  double longitude;
    public double temperatura;
    public double umidade;
    public  boolean ativarRastreamento;
}
