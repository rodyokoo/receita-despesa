package com.rodyokoo.receita_despesa.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rodyokoo.receita_despesa.model.Receita;

public interface ReceitaRepository extends CrudRepository<Receita, BigInteger>{

	List<Receita> findByDescricao(@Param("descricao") String descricao);
	
}
