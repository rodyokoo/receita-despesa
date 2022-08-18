package com.rodyokoo.receita_despesa.repository;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.rodyokoo.receita_despesa.model.Despesa;

public interface DespesaRepository extends CrudRepository<Despesa, BigInteger>{

}
