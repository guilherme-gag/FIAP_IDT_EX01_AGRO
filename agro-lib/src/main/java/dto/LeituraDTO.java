package dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class LeituraDTO implements Serializable {

    private int droneId;

    @Range(min=-90, max=90)
    private double latitude;

    @Range(min=-180, max=180)
    private double longitude;

    @Range(min=-25, max=40)
    private double temperatura;

    @Range(min=0, max=100)
    private double umidade;

    private boolean ativarRastreamento;

    private boolean valido;

}
