import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.retoalura.conversormoneda.modelos.Conversion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        int seleccion = 0;
        String direccion = "https://v6.exchangerate-api.com/v6/a9be6b84b2b5d0b7aee9e4df/pair/USD/MXN";

        while(seleccion != 7){
            System.out.println("!Bienvenido al conversor de moneda¡");
            System.out.println("1) Dólar (USD) =>> Peso argentino (ARS)");
            System.out.println("2) Peso argentino (ARS) =>> Dólar (USD)");
            System.out.println("3) Dólar (USD) =>> Real brasileño (BRL)");
            System.out.println("4) Real brasileño (BRL) =>> Dólar (USD)");
            System.out.println("5) Dólar (USD) =>> Peso Colombiano (COP)");
            System.out.println("6) Peso colombiano (COP) =>> Dólar (USD)");
            System.out.println("7) Salir");
            seleccion = lectura.nextInt();

            //El apartado de switch nos ayudara a cambiar la direccion de la pagina web dependiendo de la conversion deseada.
            switch (seleccion){
                case 1:
                    direccion = "https://v6.exchangerate-api.com/v6/a9be6b84b2b5d0b7aee9e4df/pair/USD/ARS";

                    break;
                case 2:
                    direccion = "https://v6.exchangerate-api.com/v6/a9be6b84b2b5d0b7aee9e4df/pair/ARS/USD";

                    break;
                case 3:
                    direccion = "https://v6.exchangerate-api.com/v6/a9be6b84b2b5d0b7aee9e4df/pair/USD/BRL";

                    break;
                case 4:
                    direccion = "https://v6.exchangerate-api.com/v6/a9be6b84b2b5d0b7aee9e4df/pair/BRL/USD";

                    break;
                case 5:
                    direccion = "https://v6.exchangerate-api.com/v6/a9be6b84b2b5d0b7aee9e4df/pair/USD/COP";

                    break;
                case 6:
                    direccion = "https://v6.exchangerate-api.com/v6/a9be6b84b2b5d0b7aee9e4df/pair/COP/USD";
                    break;

                case 7:
                    System.out.println("Salir");
                    break;

                default:
                    System.out.println("Opción no valida");
                    break;
            }
            try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                //Obtenemos el archivo .json de la API
                String json = response.body();

                //Una vez que tenemos la información en formato json con gson.jar podremos tomar información especifica
                Conversion miconversion = gson.fromJson(json, Conversion.class);
                System.out.println("La conversión de 1 "+ miconversion.base_code() + " a " + miconversion.target_code() + " es: " + miconversion.conversion_rate());

            }catch (NumberFormatException e){
                System.out.println("Ocurrio un error: ");
                System.out.println(e.getMessage());
            }catch (IllegalArgumentException e){
                System.out.println("Error en la URI, verifique la dirección");
            }
        }
    }
}