import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        Integer opcao = 99;
        Scanner input = new Scanner(System.in);
        String url = "";
        String entrada = "";

        //Menu
        while(opcao != 0){

            System.out.println("\n=================== MENU ===================");
            System.out.println();
            System.out.println("1 - Top 10 Filmes Mais Classificados");
            System.out.println("2 - Top 3 Series Mais Classificados");
            System.out.println("3 - Top 3 Filmes Mais Populares");
            System.out.println("4 - Top 3 Series Mais Populares");
            System.out.println("\n0 - SAIR");
            System.out.println("\n============================================");
            System.out.println();
            System.out.println("Digite a opcao desejada:");

            entrada = input.nextLine();
            
            if(entrada.equals("1") 
            || entrada.equals("2")
            || entrada.equals("3")
            || entrada.equals("4")
            || entrada.equals("0")){
            
                opcao = Integer.parseInt(entrada);

                switch(opcao) {
                    case 1: url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
                        System.out.println("\n============================================");
                        System.out.println("TOP 10 FILMES COM AS MELHORES CLASSIFICACOES");
                        System.out.println("============================================");
                        break;
                    case 2: url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
                        System.out.println("\n============================================");
                        System.out.println("TOP 3 SERIES COM AS MELHORES CLASSIFICACOES");
                        System.out.println("============================================");
                        break;
                    case 3: url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
                        System.out.println("\n============================================");
                        System.out.println("TOP 3 FILMES MAIS POPULARES");
                        System.out.println("============================================");
                        break;
                    case 4: url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
                        System.out.println("\n============================================");
                        System.out.println("TOP 3 SERIES MAIS POPULARES");
                        System.out.println("============================================");
                        break;
                    case 0: 
                        input.close();
                        System.out.println("---------------------------------------------");
                        System.out.println("             VOLTE SEMPRE !!");
                        System.out.println("----------------------------------------------");
                        break;
                    default: 
                        System.out.println("\n--------------------------------------\n");
                        System.out.println("Opcao invalida ! Escolha novamente...");
                        System.out.println("\n--------------------------------------\n");
                        continue;
                }

                if(opcao < 5 && opcao > 0){
                    // Fazer conexão HTTP
                    HttpClient client = HttpClient.newHttpClient();
                    URI uri = URI.create(url);
                    HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
                    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                    String body = response.body();

                    // Extrair os dados necessarios (titulo, poster, classificação)
                    JsonParser parser = new JsonParser();
                    List<Map<String, String>> listaDeFilmes = parser.parse(body);

                    String estrela = "";
                    String vazio = "";
                    Integer totalEstrela = 10;

                    String uniCode;
                    String uniCodeAzul = "\u001b[30;1m\u001b[44;1m";
                    String uniCodeVerde = "\u001b[30;1m\u001b[44;1m";
                    String uniCodeAmarelo = "\u001b[30;1m\u001b[44;1m";
                    String uniCodeVermelho = "\u001b[30;1m\u001b[44;1m";

                    // Exibir e manipular os dados
                    for (Map<String,String> filme : listaDeFilmes) {

                        System.out.println("\nTitulo: \u001b[1m" + filme.get("title") + "\u001b[0m");
                        System.out.println("Capa: \u001b[1m" + filme.get("image") + "\u001b[0m");

                        Double estrelaDouble = Double.parseDouble(filme.get("imDbRating"));
                        Integer qtdeEstrela = (int) Math.round(estrelaDouble);

                        if(qtdeEstrela < 5){
                            uniCode = uniCodeVermelho;
                        } else if(qtdeEstrela <= 7){
                            uniCode = uniCodeAmarelo;
                        } else if(qtdeEstrela <= 10){
                            uniCode = uniCodeVerde;
                        }else{
                            uniCode = uniCodeAzul;
                        }
                        
                        System.out.println(uniCode + "Classificacao: " + filme.get("imDbRating") + " \u001b[0m");

                        for (int i = 0; i < qtdeEstrela; i++) {
                            estrela += "[x]";
                        }

                        Integer qtdeVazio = totalEstrela - qtdeEstrela;
                        for (int i = 0; i < qtdeVazio; i++) {
                            vazio += "[ ]";
                        }

                        System.out.println("\u001b[43m" + estrela + "\u001b[m" + vazio);

                        estrela = "";
                        vazio = "";

                        System.out.println(" \n--------------------------------------");
                    }
                }
            }
        }
    }
}
