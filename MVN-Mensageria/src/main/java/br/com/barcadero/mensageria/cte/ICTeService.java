package br.com.barcadero.mensageria.cte;

import br.com.barcadero.mensageria.soap.EnumTpAmbiente;

public interface ICTeService {
	
	public void setAmbiente(EnumTpAmbiente tpAmbi) throws Exception;
	public void setServico(EnumCTeServicos cteServico);
	public String getURLCteRecepcao();
	public String getURLCteRetRecepcao();
	public String getURLCteInutilizacao();
	public String getURLCteConsultaProtocolo();
	public String getURLCteStatusServico();
	public String getURLCteRecepcaoEvento();

}
