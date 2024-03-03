package Controllers.Etudiant;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VoiceAssistantpopup implements Initializable {
    @FXML
    private Popup popup;
    @FXML
    private Label label;

    @FXML
    private Label response;
    public void setPopup(Popup popup) {
        this.popup = popup;
    }
    @FXML
    void closePopup(ActionEvent event) {
        this.popup.hide();
    }

    public void voiceAssistant(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Configuration config= new Configuration();
                config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
                config.setDictionaryPath("src\\main\\resources\\4775.dic");
                config.setLanguageModelPath("src\\main\\resources\\4775.lm");
                try {
                    LiveSpeechRecognizer speech = new LiveSpeechRecognizer(config);
                    speech.startRecognition(true);

                    SpeechResult speechResult = null;
                    ProcessBuilder browser=new ProcessBuilder("");

                    while ((speechResult = speech.getResult()) != null) {
                        String voiceCommand = speechResult.getHypothesis();
                        System.out.println("Voice Command is " + voiceCommand);
                        Platform.runLater(() -> {
                            label.setText(voiceCommand);
                        });



                        if (voiceCommand.equalsIgnoreCase("Open Chrome")) {
                            Runtime.getRuntime().exec("cmd.exe /c start chrome ");
                        } else if (voiceCommand.equalsIgnoreCase("Close Chrome")) {
                            Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM chrome.exe");
                        }  else if(voiceCommand.equalsIgnoreCase("open youtube")){

                            browser=new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe","youtube.com");
                            //browserProcess=
                            browser.start();
                        }else if (voiceCommand.equalsIgnoreCase("saker google")) {
                            Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM chrome.exe");
                        }else if (voiceCommand.equalsIgnoreCase("khadem google")) {
                            Runtime.getRuntime().exec("cmd.exe /c start chrome ");

                        }else if (voiceCommand.equalsIgnoreCase("good job")) {
                            System.out.println("---------------------  THANKS !!! --------------------");
                            System.out.println("****** THIS PROJECT IS MADED BY TELETBUIES ***** ");
                            Platform.runLater(() -> {
                                response.setText("Thanks");
                            });

                        }else if (voiceCommand.equalsIgnoreCase("aaslema")) {
                            System.out.println("*********************     SALUT ENA ESPRITIFY !!!!!  kifeh n3awenk ********************");
                            Platform.runLater(() -> {
                                 response.setText("Salut! ena Espritifia!!! kifeh najem n3awnek");
                            });

                        }else if (voiceCommand.equalsIgnoreCase("hello")) {
                            System.out.println("*********************     Hello You Want Help !!  ********************");
                            Platform.runLater(() -> {
                                response.setText("Hello to you too, how can I help You? ");
                            });

                        }else if (voiceCommand.equalsIgnoreCase("who are you")) {
                            System.out.println("*********************    IM ESPRITIFY HOW CAN I HELP YOU  ********************");
                            System.out.println("*********************    YOU CAN ASK FOR HELP IF U WANT  ********************");
                            Platform.runLater(() -> {
                                response.setText("I am espritifia! How Can I Help You?");
                            });
                        }else if (voiceCommand.equalsIgnoreCase("help")) {
                            System.out.println("*********************    HEllO AGAIN  ********************");
                            System.out.println("*********************    YOU HAVE SOME VOICE COMMANDE THAT U CAN RUN   ********************");
                            System.out.println("MORE LIKE : "
                                    + "\n"
                                    + "\n"
                                    + ": OPEN CHROME \n"
                                    + "\n"
                                    + ": CLOSE CHROME \n"
                                    + "\n"
                                    + ": OPEN YOUTUBE \n"
                                    + "\n"
                                    + "\n"
                                    + ": ALSO KHADEM GOOGLE \n"
                                    + ": SAKER GOOGLE \n"
                                    + "\n"
                                    + "\n"
                                    + "\n"
                                    + "and we have study mode pack for developers "

                                    + "*********************** DONT FORGET TO TELL ME GOOD JOB CAUSE I LIKE THAT ***********");
                            Platform.runLater(() -> {
                                response.setText("\"MORE LIKE : \"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \": OPEN CHROME \\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \": CLOSE CHROME \\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \": OPEN YOUTUBE \\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \": ALSO KHADEM GOOGLE \\n\"\n" +
                                        "                            + \": SAKER GOOGLE \\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \"\\n\"\n" +
                                        "                            + \"and we have study mode pack for developers \"\n" +
                                        "\n" +
                                        "                            + \"*********************** DONT FORGET TO TELL ME GOOD JOB CAUSE I LIKE THAT ***********");
                            });
                            while((speechResult = speech.getResult())== null) {

                            }
                        } else if(voiceCommand.equalsIgnoreCase("study mode")){

                            browser=new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe","https://www.youtube.com/watch?v=ascuhpKJGlk&ab_channel=RELAXATION%28LoFi%26CHILL%29");
                            //browserProcess=
                            browser.start();
                            Platform.runLater(() -> {
                                response.setText("Youtube est entrain de charger");
                            });
                            Runtime.getRuntime().exec("cmd.exe /c start code ");

                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        new Thread(task).start();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        voiceAssistant();
    }
}
