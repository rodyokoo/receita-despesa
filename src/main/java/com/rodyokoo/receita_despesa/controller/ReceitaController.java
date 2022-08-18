package com.rodyokoo.receita_despesa.controller;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodyokoo.receita_despesa.model.Receita;
import com.rodyokoo.receita_despesa.repository.ReceitaRepository;

@RestController
@RequestMapping(path="/receita")
public class ReceitaController {

	@Autowired
	private ReceitaRepository receitaRepository;
	
	@PostMapping(value="/adicionar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Receita> novaReceita(@RequestBody Receita receita){

		if(!isDescricaoDuplicate(receita)) {
			return ResponseEntity
					 .created(URI
							 .create(String.format("/receita/%s", receita.getDescricao().replace(" ", "%"))))
					 .body(receitaRepository.save(receita));
		} else  {
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
	}
	
	@GetMapping("/")
	public Iterable<Receita> all() {
		return receitaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Receita> getById(@PathVariable("id") BigInteger id){
		return receitaRepository.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Receita> updateReceita(@PathVariable("id") BigInteger id, @RequestBody Receita receita){
		
		Optional<Receita> receitaData = receitaRepository.findById(id);
		
		Receita receitaPut = new Receita();

		if(receitaData.isPresent()) {
			receitaPut = receitaData.get();
			receitaPut.setDescricao(receita.getDescricao());
			receitaPut.setValor(receita.getValor());
			receitaPut.setData(receita.getData());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(!isDescricaoDuplicate(receita)) {
			return new ResponseEntity<>(receitaRepository.save(receitaPut), HttpStatus.OK);			
		} else {
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
	}
	
	private boolean isDescricaoDuplicate(Receita receita) {
		List<Receita> list = receitaRepository.findByDescricao(receita.getDescricao());
		
		int controller = 0;
		for(Receita r : list) {
			if(r.getData().getMonth().equals(receita.getData().getMonth())) {
				if(r.getData().getYear() == receita.getData().getYear()){
					controller++;
				}
			}
		}
		if(controller > 0) {
			return true;
		} else {
			return false;
		}
	}
}
