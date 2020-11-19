package ARL.tesi.modelobject.report;


public class reportObject {

    private String tipo;
    private String sottotipo;
    private float value;

    public reportObject() {
    }

    public reportObject(String tipo, String sottotipo, float value) {
        this.tipo = tipo;
        this.sottotipo = sottotipo;
        this.value = value;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSottotipo() {
        return sottotipo;
    }

    public void setSottotipo(String sottotipo) {
        this.sottotipo = sottotipo;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
