package jpasswortbunker.mgm.sample;

public class Model {

    private String text;
    private Presenter presenter;


    public Model(Presenter presenter) {
        this.presenter = presenter;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public void updateGui(){
        presenter.updateGui();
    }

}
