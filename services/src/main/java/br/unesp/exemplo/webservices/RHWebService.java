package br.unesp.exemplo.webservices;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.unesp.exemplo.api.valueobjects.PessoaVO;

@Component
public class RHWebService {

	@Value("${webservices.rh.url}")
	private String rhWsUrl;
	
	public PessoaVO buscarPessoaPorCPF(String cpf){
		String url = rhWsUrl.concat("/servidoresPublico/cpf::").concat(cpf);
		try{
			HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
			if(jsonResponse.getStatus() == HttpStatus.OK.value()){
				JSONArray array = jsonResponse.getBody().getArray();
				if(array.length() > 0){
					return new PessoaVO(array.getJSONObject(0).getString("nome"),array.getJSONObject(0).getString("email"),array.getJSONObject(0).getString("cpf"));
				}
			}
		}catch (UnirestException e) {
			throw new RuntimeException("Erro ao consultar WS do RH", e);
		}
		return null;
	}
	
}
