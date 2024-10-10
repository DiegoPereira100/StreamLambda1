package fatecpg.br.edu;
import fatecpg.br.edu.service.Comment;
import fatecpg.br.edu.service.CommentRefac;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String url = "https://jsonplaceholder.typicode.com/comments";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new java.net.URI(url))
                    .GET() // Método GET
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Comment.class, new CommentRefac());
            Gson gson = gsonBuilder.create();

            List<Comment> comments = gson.fromJson(response.body(), new TypeToken<List<Comment>>(){}.getType());

            List<Comment> filteredComments = comments.stream()
                    .filter(comment -> comment.body().length() > 20) 
                    .collect(Collectors.toList());

            filteredComments.forEach(comment -> {
                System.out.println();
                System.out.println("Nome do usuário: " + comment.name());
                System.out.println("E-mail do usuário: " + comment.email());
                System.out.println("Comentário: " + comment.body());
                System.out.println("--------------------------------------");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

