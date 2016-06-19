package br.com.barcadero.mensageria.cte;

import br.com.barcadero.mensageria.soap.EnumTpAmbiente;

public interface ICTeService {
	
	public void setAmbiente(EnumTpAmbiente tpAmbi) throws Exception;
	public void setServico(EnumCTeServicos cteServico);
	public void getURLCteRecepcao();
	public void getURLCteRetRecepcao();
	public void getURLCteInutilizacao();
	public void getURLCteConsultaProtocolo();
	public void getURLCteStatusServico();
	public void getURLCteRecepcaoEvento();

}
