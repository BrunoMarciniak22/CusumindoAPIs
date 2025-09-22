//ESSAS SÃO AS IMPORTAÇÕES DAS BIBLIOTECAS NECESSÁRIAS PARA O FUNCIONAMENTO DO CÓDIGO 
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner; 

//CLASSE PRINCIPAL DO PROGRAMA 
public class APIConsumer { 
    public static void main(String[] args) {//método principal que executa o programa

        int iAPIcat = 0;
        int iAPIpiada =0;
        int iAPIcep = 0;
        

        
        
        
        
        //------------------------------------------------------------------------------------------------
        //ESTRUTURA DE REPETIÇÃO PARA EXIBIR O MENU ATÉ O USUÁRIO ESCOLHER SAIR
        Scanner scanner = new Scanner(System.in);//importação da classe Scanner para ler a entrada do usuário 
        int opcao; 
      do {

        System.out.println("Menu:");
        System.out.println("1 - Consumir API Fatos de gatos");
        System.out.println("2 - Consumir API Piadas");
        System.out.println("3 - Procurar CEP");
        System.out.println("4 - Consultar a qauntidade de chamadas feitas a cada API!!!");
        System.out.println("5 - Sair");
        System.out.print("Escolha uma opção: ");
        opcao = scanner.nextInt();

     //estrutura switch para ler executar a opção escolhida pelo usuário 
        switch(opcao){
            case 1:
                System.out.println("Consumindo API 1...");
                consumirAPIFatosDeGatos(); 
                iAPIcat++;//incrementa o contador de chamadas da API de fatos de gatos 
                // Lógica para consumir a API 1
                break;
            case 2:
                System.out.println("Consumindo API 2...");
                consumirAPIPiadas(); 
                iAPIpiada++;//incrementa o contador de chamadas da API de piadas 
                // Lógica psDeCaeara consumir a API 2
                break;
            case 3:
                System.out.print("Digite o CEP: ");
                String cep = scanner.next();
                System.out.println("Procurando CEP: " + cep);
                consumirAPICep(cep); 
                iAPIcep++; 
                // Lógica para procurar o CEP
                break;
            case 4:
                System.out.println("Quantidade de chamadas feitas a cada API:"); 
                System.out.println("API Fatos de Gatos: " + iAPIcat + " chamadas"); 
                System.out.println("API Piadas: " + iAPIpiada + " chamadas"); 
                System.out.println("API CEP: " + iAPIcep + " chamadas"); 
                break; 
            case 5:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }  while(opcao != 5);//repete o menu até o usuário escolher sair 
      scanner.close();
    } 




//------------------------------------------------------------------------------------------------
    //OS MÉTODOS ABAIXO SÃO RESPONSAVEIS POR CONSUMIR AS APIs E EXTRAIR OS DADOS DE CADA UMA DELAS


    //MÉTODO PARA CONSUMIR A API DE FATOS DE GATOS  
     public static void consumirAPIFatosDeGatos() {
        //estrutura try-catch para tratar possíveis erros durante a requisição HTTP 
        try {
            String Url1Gato = "https://catfact.ninja/fact";
            URL url = new URL(Url1Gato); 
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection(); 
            conexao.setRequestMethod("GET"); 
            BufferedReader leitor = new BufferedReader(new java.io.InputStreamReader(conexao.getInputStream())); 
            String linha;
            StringBuilder resposta = new StringBuilder(); 
            while ((linha = leitor.readLine()) != null) { 
                resposta.append(linha); 
            } 
            leitor.close();
            String json = resposta.toString(); 
            System.out.println("Resposta da API Fatos de Gatos: " + json); 
            extrairDadosGato(json);
           

        } catch (Exception erro) {
            System.out.println("Erro ao consumir a API Fatos de Gatos: " + erro.getMessage()); 
        }
    }

        public static void extrairDadosGato(String json){
            if(json.contains("\"fact\":")){
                String fact = extrairCampoGato(json, "fact");
                System.out.println("Fato de Gato: " + fact);
            }
            if(json.contains("\"length\":")){
                String length = extrairCampoGato(json, "length");
                System.out.println("Comprimento do Fato: " + length); 
            }
        }





        //METODO PARA CONSUMIR A API DE PIADAS 
        public static void consumirAPIPiadas() {
            //estrutura try-catch para tratar possíveis erros durante a requisição HTTP 
            try {
                String Url2Piadas = "https://api.chucknorris.io/jokes/random";
                URL url = new URL(Url2Piadas); 
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection(); 
                conexao.setRequestMethod("GET"); 
                BufferedReader leitor = new BufferedReader(new java.io.InputStreamReader(conexao.getInputStream())); 
                String linha;
                StringBuilder resposta = new StringBuilder(); 
                while ((linha = leitor.readLine()) != null) { 
                    resposta.append(linha); 
                } 
                leitor.close();
                String json = resposta.toString(); 
                extrairDadosPiadas(json);

            } catch (Exception erro) {
                System.out.println("Erro ao consumir a API Piadas: " + erro.getMessage()); 
            } 
        }
        public static void extrairDadosPiadas(String json){
         
            if(json.contains("\"id\":")){
                String id = extrairCampoPiadas(json, "id");
                System.out.println("ID da Piada: " + id);
            }
            if(json.contains("\"value\":")){
                String value = extrairCampoPiadas(json, "value");
                System.out.println("Piada: " + value);
        }     
}







        //MÉTODO PARA CONSUMIR A API DE CEP 
        public static void consumirAPICep(String cep){
            //estrutura try-catch para tratar possíveis erros durante a requisição HTTP 
            try {
                String Url3Cep = "https://brasilapi.com.br/api/cep/v2/" + cep;
                URL url = new URL(Url3Cep); 
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection(); 
                conexao.setRequestMethod("GET"); 
                BufferedReader leitor = new BufferedReader(new java.io.InputStreamReader(conexao.getInputStream())); 
                String linha;
                StringBuilder resposta = new StringBuilder(); 
                while ((linha = leitor.readLine()) != null) { 
                    resposta.append(linha); 
                } 
                leitor.close();
                String json = resposta.toString(); 
                System.out.println("Resposta da API CEP: " + json); 
                extrairDadosCep(json);

            } catch (Exception erro) {
                System.out.println("Erro ao consumir a API CEP: " + erro.getMessage()); 
            }  
        }






//-----------------------------------------------------------------------------------------------
        //MÉTODOS PARA EXTRAIR OS DADOS DE CADA UMA DAS APIS 


            //método para extrair os dados do JSON retornado pela API de CEP
            public static void extrairDadosCep(String json){

                //utilizando a função if para verificar se o campo existe no JSON antes de tentar extrair o valores inseridos pelos usuários
                if(json.contains("\"cep\":")){
                    String cep = extrairCampoCep(json, "cep");
                    System.out.println("CEP: " + cep);
                }
                if(json.contains("\"state\":")){
                    String state = extrairCampoCep(json, "state");
                    System.out.println("Estado: " + state); 
                }
                if(json.contains("\"city\":")){
                    String city = extrairCampoCep(json, "city");
                    System.out.println("Cidade: " + city); 
                }
                if(json.contains("\"neighborhood\":")){
                    String neighborhood = extrairCampoCep(json, "neighborhood");
                    System.out.println("Bairro: " + neighborhood); 
                }
                if(json.contains("\"street\":")){
                    String street = extrairCampoCep(json, "street");
                    System.out.println("Rua: " + street); 
                }
            } 





            //------------------------------------------------------------------------------------------------ 
            //método para extrair um campo específico do JSON retornado pela API de CEP 
            public static String extrairCampoCep(String json, String campo){
                String padrao = "\"" + campo + "\":\""; 
                int inicio = json.indexOf(padrao) + padrao.length(); 
                int fim = json.indexOf("\"", inicio); 
                return json.substring(inicio, fim); 
            } 







            //------------------------------------------------------------------------------------------------
            //método para extrair um campo específico do JSON retornado pela API de fatos de gatos 
            public static String extrairCampoGato(String json, String campo){
                String padrao = "\"" + campo + "\":\""; 
                int inicio = json.indexOf(padrao) + padrao.length(); 
                int fim = json.indexOf("\"", inicio); 
                return json.substring(inicio, fim); 
            } 






            //------------------------------------------------------------------------------------------------ 
            //método para extrair um campo específico do JSON retornado pela API de piadas 
            public static String extrairCampoPiadas(String json, String campo){
                String padrao = "\"" + campo + "\":\""; 
                int inicio = json.indexOf(padrao) + padrao.length(); 
                int fim = json.indexOf("\"", inicio); 
                return json.substring(inicio, fim); 
            } 
    
}
           
    




















 