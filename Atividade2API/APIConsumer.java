//ESSAS SÃO AS IMPORTAÇÕES DAS BIBLIOTECAS NECESSÁRIAS PARA O FUNCIONAMENTO DO CÓDIGO 
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


//CLASSE PRINCIPAL DO PROGRAMA 
public class APIConsumer { 
    public static void main(String[] args) {//método principal que executa o programa
       
        ArrayList<String> historicoChamadas = new ArrayList<>();

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
        System.out.println("4 - Consultar a quantidade de chamadas feitas por cada API!!!");
        System.out.println("5 - Consultar a quantidade de vezes que foram chamadas todas as APIs!!!");
        System.out.println("6 - Consultar todas as APIs de uma vez só!!!"); 
        System.out.println("7 - Consultar o histórico de chamadas das APIs!!!"); 
        System.out.println("8 - Consultar API aleatória!!! ");
        System.out.println("9 - Consultar a API mais escolhida!!!"); 
        System.out.println("10 - Sair");
        System.out.print("Escolha uma opção: ");
        opcao = scanner.nextInt();

     //estrutura switch para ler executar a opção escolhida pelo usuário 
        switch(opcao){
            case 1:
                System.out.println("Consumindo API 1...");
                consumirAPIFatosDeGatos(); 
                iAPIcat++;//incrementa o contador de chamadas da API de fatos de gatos 
                // Lógica para consumir a API 1
                adicionarAoHistorico(historicoChamadas, "API Fatos de Gatos chamada");
                break;
            case 2:
                System.out.println("Consumindo API 2...");
                consumirAPIPiadas(); 
                iAPIpiada++;//incrementa o contador de chamadas da API de piadas 
                // Lógica psDeCaeara consumir a API 2
                adicionarAoHistorico(historicoChamadas, "API de chamadas de piadas");
                break;
            case 3:
                System.out.print("Digite o CEP: ");
                String cep = scanner.next();
                System.out.println("Procurando CEP: " + cep);
                consumirAPICep(cep); 
                iAPIcep++; 
                adicionarAoHistorico(historicoChamadas, "API de chamadas cep");
                // Lógica para procurar o CEP
                break;
            case 4:
                System.out.println("Quantidade de chamadas feitas por cada API:"); 
                System.out.println("API Fatos de Gatos: " + iAPIcat + " chamadas"); 
                System.out.println("API Piadas: " + iAPIpiada + " chamadas"); 
                System.out.println("API CEP: " + iAPIcep + " chamadas"); 
                break; 
            case 5:
                System.out.println("Quantidade total de chamadas feitas por todas as APIs: " + totalChamadasAPI(iAPIcat, iAPIpiada, iAPIcep) + " chamadas ");
                break;
            case 6:
                System.out.println("Consultando todas as APIs de uma vez só..."); 
                consultarTodasAPIs(); 
                iAPIcat++; 
                iAPIpiada++;
                iAPIcep++; 
                adicionarAoHistorico(historicoChamadas, "Todas as APIS");
             break;
             case 7:
                 System.out.println("Últimas 5 chamadas das APIs:");
                    if (historicoChamadas.isEmpty()) {
                 System.out.println("Nenhuma chamada registrada ainda.");
                    } else {
                        for (int i = 0; i < historicoChamadas.size(); i++) {
                 System.out.println((i + 1) + " - " + historicoChamadas.get(i));
                        }
                    }
                break;
            case 8: 
                System.out.println("Consultando uma API aleatória..."); 
                consultarAPIAleatoria(); 
                iAPIcat++; 
                iAPIpiada++; 
                iAPIcep++;
                adicionarAoHistorico(historicoChamadas, "API aleatória");
                break; 
            case 9:
                System.out.println("Consultando a API mais escolhida..."); 
                consultarAPImaisEscolhida(iAPIcat, iAPIpiada, iAPIcep); 
                break; 

            case 10:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }  while(opcao != 10);//repete o menu até o usuário escolher sair 
      scanner.close();
    } 
    public static void adicionarAoHistorico(ArrayList<String> historico, String entrada) {
    if (historico.size() == 5) {
        historico.remove(0); // remove o mais antigo
    }
    historico.add(entrada);
}

    

   

    

//-------------------------------------------------------------------------------------------------
    //Método para calcular o total de chamadas feitas por todas as APIs 
    public static int totalChamadasAPI(int iAPIcat, int iAPIpiada, int iAPIcep){
        return iAPIcat + iAPIpiada + iAPIcep; 
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

            //------------------------------------------------------------------------------------------------ 
            //MÉTODO UTILIADO PARA CONSULTAR TODAS AS APIS
            public static void consultarTodasAPIs(){
                System.out.println("Consumuindo API de gatos: "); 
                consumirAPIFatosDeGatos();

                System.out.println("\n");
                System.out.println("Consumindo API de piadas: "); 
                consumirAPIPiadas();


                System.out.println("\n"); 
                Scanner scanner = new Scanner(System.in); 
                System.out.print("Digite o CEP: "); 
                String cep = scanner.next(); 
                consumirAPICep(cep); 

            }




            //------------------------------------------------------------------------------------------------ 
            //MÉTDO PARA CONSULTAR UMA API ALEATÓRIA 
            public static void consultarAPIAleatoria(){
                int apiAleatoria = (int) (Math.random() * 3) + 1; 
                switch(apiAleatoria){
                    case 1:
                        System.out.println("Consumindo API de gatos: "); 
                        consumirAPIFatosDeGatos();
                        break;
                    case 2:
                        System.out.println("Consumindo API de piadas: "); 
                        consumirAPIPiadas();
                        break;
                    case 3:
                        Scanner scanner = new Scanner(System.in); 
                        System.out.print("Digite o CEP: "); 
                        String cep = scanner.next(); 
                        consumirAPICep(cep); 
                        break;
                }
            } 

            //------------------------------------------------------------------------------------------------ 
            //MÉTODO PARA CONSULTAR A ESTATISTICA DA API MAIS ESCOLHIDA

            public static void consultarAPImaisEscolhida(int iAPIcat, int iAPIpiada, int iAPIcep){
                if(iAPIcat > iAPIpiada && iAPIcat > iAPIcep){
                    System.out.println("A API de fatos de gatos foi a mais escolhida com " + iAPIcat + " chamadas.");
                } else if(iAPIpiada > iAPIcat && iAPIpiada > iAPIcep){
                    System.out.println("A API de piadas foi a mais escolhida com " + iAPIpiada + " chamadas.");
                } else if(iAPIcep > iAPIcat && iAPIcep > iAPIpiada){
                    System.out.println("A API de CEP foi a mais escolhida com " + iAPIcep + " chamadas.");
                } else {
                    System.out.println("Houve um empate entre as APIs.");
                }
            } 

}

           
    




















 