
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import process.AudioTranscription;
import process.TranslateText;

import java.io.FileInputStream;



public class TextSpeech {
    public static void main(String[] args) throws Exception {

        // [START auth_cloud_explicit]
        GoogleCredentials credentials =
                GoogleCredentials.fromStream(new FileInputStream("F:\\Environment\\api-project-204667169246-bab65b95321a.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        /**
         * Add the path of audio you want extract transcript in 'wav' format
         */
        AudioTranscription audioTranscription =
                new AudioTranscription("F:\\VideoProcess\\motivational speech.wav");
        String audioTextTranscription =audioTranscription.getTranscription();
        System.out.println("Transcription:\n" + audioTextTranscription);

        /**
         * Add audioTextTranscription you want to translate
         */
        TranslateText translateText = new TranslateText(audioTextTranscription);


        }
    }
