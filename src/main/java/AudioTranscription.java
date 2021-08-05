
import com.google.cloud.speech.v1p1beta1.*;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AudioTranscription {

    String transcription;

    public   AudioTranscription(){}

    /**
     *
     * @param filePath
     */
    public  AudioTranscription(String filePath)   {

        try (SpeechClient speechClient = SpeechClient.create()) {

            // When enabled, trascription results may include punctuation
            // (available for select languages).
            boolean enableAutomaticPunctuation = true;

            // The language of the supplied audio. Even though additional languages are
            // provided by alternative_language_codes, a primary language is still required.
            String languageCode = "en";
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEnableAutomaticPunctuation(enableAutomaticPunctuation)
                            .setLanguageCode(languageCode)
                            .setAudioChannelCount(2)
                            .build();
            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);
            ByteString content = ByteString.copyFrom(data);
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(content).build();
            RecognizeRequest request =
                    RecognizeRequest.newBuilder().setConfig(config).setAudio(audio).build();
            RecognizeResponse response = speechClient.recognize(request);
            for (SpeechRecognitionResult result : response.getResultsList()) {
                // First alternative is the most probable result
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                transcription =  alternative.getTranscript();
            }
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
    }


}
