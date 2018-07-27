package com.mercado.loja.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.mercado.loja.model.PagamentoComBoleto;

@Service
public class BoletoService {
	public PagamentoComBoleto preencerPagamentoComBoleto(PagamentoComBoleto pagto, Date instante) {
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.setTime(instante);
		dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(dataVencimento.getTime());
		return pagto;
	}
}
