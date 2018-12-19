package pacote.dao;

import com.mongodb.client.MongoDatabase;

import pacote.bean.CandidatoBean;
import pacote.bean.CargoBean;
import pacote.bean.EleicaoBean;
import pacote.config.ConfigStatus;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;
import pacote.config.ConfigStatus;

public class EleicaoDB extends ConexaoMongo {

	public boolean cadastrar(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				System.out.println(campos.getDataInicio());
				Document documento = new Document();
				documento.put("nome", campos.nome );
				documento.put("descricao", campos.descricao );
				documento.put("status", campos.status );
				documento.put("dt_inicio", campos.dataInicio );
				documento.put("dt_fim", campos.dataFim );
				documento.put("dt_criacao", new Date());
				documento.put("cargos", null);
				colection.insertOne(documento);
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizar(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				Document documento = new Document();
				documento.put("nome", campos.nome );
				documento.put("descricao", campos.descricao );
				documento.put("status", campos.status );
				documento.put("status", campos.status );
				documento.put("dt_inicio", campos.dataInicio );
				documento.put("dt_fim", campos.dataFim );
				documento.put("dt_criacao", new Date());
				colection.updateOne(eq("_id", new ObjectId(campos.id)), new Document("$set", documento));
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean adicionarCargo(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			List<String>  idsCargo = new ArrayList<String>();
			for(CargoBean c : campos.getCargos()) {
				idsCargo.add(c.id);
			}
			if(colection != null) {
				Document documento = new Document();
				documento.put("cargos", idsCargo);
				colection.updateOne(eq("_id", new ObjectId(campos.id)), new Document("$set", documento));
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizarStatus(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			List<String>  idsCargo = new ArrayList<String>();
			
			if(colection != null) {
				Document documento = new Document();
				documento.put("status", campos.getStatus());
				colection.updateOne(eq("_id", new ObjectId(campos.getId())), new Document("$set", documento));
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean excluir(EleicaoBean campos) {
		ConexaoMongo conn = new ConexaoMongo();
		try {
			
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				Document documento = new Document();
				documento.put("_id",  new ObjectId(campos.id) );
				colection.deleteOne(documento);
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public List<EleicaoBean> listarEleicoes(){
		ArrayList<EleicaoBean> lista = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find().iterator();
				try {
					lista = new ArrayList<EleicaoBean>();
					while (cursor.hasNext()) {
						EleicaoBean elemento = new EleicaoBean();
				        Document doc = cursor.next();
				        elemento.id = doc.get("_id").toString();
				        elemento.nome = doc.get("nome", "").toString();
				        elemento.descricao = doc.get("descricao", "").toString();
				        elemento.status = doc.get("status", "0").toString();
				        if(elemento.status.equals(ConfigStatus.ATIVO.valor())) {
				        	elemento.statusDescricao = ConfigStatus.DESCRICAO_ATIVO.valor();
						}else if(elemento.status.equals(ConfigStatus.INATIVO.valor())) {
							elemento.statusDescricao = ConfigStatus.DESCRICAO_INATIVO.valor();
						}
				        
				        elemento.dataInicio = doc.getDate("dt_inicio");
				        elemento.dataFim = doc.getDate("dt_fim");
				        
				        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");     
				        elemento.dataInicioDescricao = df.format(elemento.dataInicio);
				        elemento.dataFimDescricao = df.format(elemento.dataFim);
				        elemento.cargos = new ArrayList<CargoBean>();
				        
				        try {
				        	elemento.cargosSelected = (List<String>) doc.get("cargos");
				        	for(String it : elemento.getCargosSelected()) {
				        		elemento.getCargos().add(new CargoDB().getCargo(it));
				        	}
				        }catch(Exception exx) {
				        	exx.printStackTrace();
				        }
				        
				        lista.add(elemento);
				    }
				} catch(Exception exx){
					exx.printStackTrace();
				}finally {
				    cursor.close();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<EleicaoBean> listarEleicoesAtivasNaoIniciadas(){
		ArrayList<EleicaoBean> lista = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(and(eq("status", ConfigStatus.ATIVO.valor()), gt("dt_inicio", new Date()))).iterator();
				try {
					lista = new ArrayList<EleicaoBean>();
					while (cursor.hasNext()) {
						EleicaoBean elemento = new EleicaoBean();
				        Document doc = cursor.next();
				        elemento.id = doc.get("_id").toString();
				        elemento.nome = doc.get("nome", "").toString();
				        elemento.descricao = doc.get("descricao", "").toString();
				        elemento.status = doc.get("status", "0").toString();
				        if(elemento.status.equals(ConfigStatus.ATIVO.valor())) {
				        	elemento.statusDescricao = ConfigStatus.DESCRICAO_ATIVO.valor();
						}else if(elemento.status.equals(ConfigStatus.INATIVO.valor())) {
							elemento.statusDescricao = ConfigStatus.DESCRICAO_INATIVO.valor();
						}
				        
				        elemento.dataInicio = doc.getDate("dt_inicio");
				        elemento.dataFim = doc.getDate("dt_fim");
				        
				        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");     
				        elemento.dataInicioDescricao = df.format(elemento.dataInicio);
				        elemento.dataFimDescricao = df.format(elemento.dataFim);
				        elemento.cargos = new ArrayList<CargoBean>();
				        
				        try {
				        	elemento.cargosSelected = (List<String>) doc.get("cargos");
				        	for(String it : elemento.getCargosSelected()) {
				        		elemento.getCargos().add(new CargoDB().getCargo(it));
				        	}
				        }catch(Exception exx) {
				        	exx.printStackTrace();
				        }
				        
				        Document doc1 = null;
				        if(doc.get("cargos_candidato") instanceof Document) {
				        	doc1 = (Document) doc.get("cargos_candidato");
				        }
				        
				        if(doc1 != null) {
				        	for(CargoBean c: elemento.getCargos()) {
				        		List<String> lista_candidatos_cargo = (List<String>) doc1.get(c.getId());
				        		List<CandidatoBean> lista_gerada_candidato = new ArrayList<CandidatoBean>();
				        		if(lista_candidatos_cargo != null) {
				        			for(String candidato_cargo: lista_candidatos_cargo) {
				        				if(candidato_cargo != null) {
				        					CandidatoBean ca = new CandidatoDB().getCandidato(candidato_cargo);
						        			lista_gerada_candidato.add(ca);
				        				}else {
				        					lista_gerada_candidato.add(null);
				        				}
					        		}
					        		c.setVagaCandidato(lista_gerada_candidato);
				        		}
				        	}
				        }
				        
				        lista.add(elemento);
				    }
				} catch(Exception exx){
					exx.printStackTrace();
				}finally {
				    cursor.close();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	
	@SuppressWarnings("unchecked")
	public EleicaoBean getEleicao(String id) {
		EleicaoBean elemento = new EleicaoBean();
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(eq("_id", new ObjectId(id)) ).iterator();
				while (cursor.hasNext()) {
					Document doc = cursor.next();
			        elemento.id = doc.get("_id").toString();
			        elemento.nome = doc.get("nome", "").toString();
			        elemento.descricao = doc.get("descricao", "").toString();
			        elemento.status = doc.get("status", "0").toString();
			        if(elemento.status.equals(ConfigStatus.ATIVO.valor())) {
			        	elemento.statusDescricao = ConfigStatus.DESCRICAO_ATIVO.valor();
					}else if(elemento.status.equals(ConfigStatus.INATIVO.valor())) {
						elemento.statusDescricao = ConfigStatus.DESCRICAO_INATIVO.valor();
					}
			        
			        elemento.dataInicio = doc.getDate("dt_inicio");
			        elemento.dataFim = doc.getDate("dt_fim");
			        
			        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");     
			        elemento.dataInicioDescricao = df.format(elemento.dataInicio);
			        elemento.dataFimDescricao = df.format(elemento.dataFim);
			        elemento.cargos = new ArrayList<CargoBean>();
			        elemento.cargosSelected = (List<String>) doc.get("cargos");
			        for(String it : elemento.getCargosSelected()) {
			        	elemento.getCargos().add(new CargoDB().getCargo(it));
			        }
			        Document doc1 = null;
			        if(doc.get("cargos_candidato") instanceof Document) {
			        	doc1 = (Document) doc.get("cargos_candidato");
			        }
			        
			        if(doc1 != null) {
			        	for(CargoBean c: elemento.getCargos()) {
			        		List<String> lista_candidatos_cargo = (List<String>) doc1.get(c.getId());
			        		List<CandidatoBean> lista_gerada_candidato = new ArrayList<CandidatoBean>();
			        		if(lista_candidatos_cargo != null) {
			        			for(String candidato_cargo: lista_candidatos_cargo) {
			        				if(candidato_cargo != null) {
			        					CandidatoBean ca = new CandidatoDB().getCandidato(candidato_cargo);
					        			lista_gerada_candidato.add(ca);
			        				}else {
			        					lista_gerada_candidato.add(null);
			        				}
				        		}
				        		c.setVagaCandidato(lista_gerada_candidato);
			        		}
			        	}
			        }
				}
			}
			return elemento;
		}catch(Exception ex) {
			ex.printStackTrace();
			return  null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean excluirCandidatoEleicao(String id_candidato) {
		boolean retorno = false;
		EleicaoBean elemento = new EleicaoBean();
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find().iterator();
				while (cursor.hasNext()) {
					Document doc = cursor.next();
					elemento.id = doc.get("_id").toString();
			        elemento.cargos = new ArrayList<CargoBean>();
			        elemento.cargosSelected = (List<String>) doc.get("cargos");
			        for(String it : elemento.getCargosSelected()) {
			        	elemento.getCargos().add(new CargoDB().getCargo(it));
			        }
			        Document doc1 = null;
			        Document documento = new Document();
			        if(doc.get("cargos_candidato") instanceof Document) {
			        	doc1 = (Document) doc.get("cargos_candidato");
			        }
			        
			        if(doc1 != null) {
			        	for(CargoBean c: elemento.getCargos()) {
			        		List<String> lista_candidatos_cargo = (List<String>) doc1.get(c.getId());
			        		List<String> lista_gerada_candidato = new ArrayList<String>();
			        		if(lista_candidatos_cargo != null) {
			        			int i = 0;
			        			int indice_candidato = -1;
			        			for(String candidato_cargo: lista_candidatos_cargo) {
			        				if(candidato_cargo != null) {
			        					if(candidato_cargo.equals(id_candidato)) {
			        						indice_candidato = i;
			        					}	
			        					CandidatoBean ca = new CandidatoDB().getCandidato(candidato_cargo);
						        		lista_gerada_candidato.add(ca.getId());
			        				}else {
			        					lista_gerada_candidato.add(null);
			        				}
			        				i++;
				        		}
			        			if(indice_candidato != -1) {
			        				lista_gerada_candidato.set(indice_candidato, null);
			        			}
			        			
				        		//c.setVagaCandidato(lista_gerada_candidato);
				        		doc1.put(c.getId(), lista_gerada_candidato);
			        		}
			        	}
			        	documento.put("cargos_candidato", doc1);
						colection.updateOne(eq("_id", new ObjectId(elemento.getId())), new Document("$set", documento));
			        }
				}
			}
			retorno = true;
			//return elemento;
		}catch(Exception ex) {
			ex.printStackTrace();
			//return  null;
		}
		return retorno;
	}
	
	
	@SuppressWarnings("unchecked")
	public  Object getGambi(String id) {
		Document doc1 = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(eq("_id", new ObjectId(id)) ).iterator();
				while (cursor.hasNext()) {
					Document doc = cursor.next();
			     
			        
			        if(doc.get("cargos_candidato") instanceof Document) {
			        	doc1 = (Document) doc.get("cargos_candidato");
			        }
			    }
			}
			return doc1;
		}catch(Exception ex) {
			ex.printStackTrace();
			return  null;
		}
	}
	
	public boolean preencherVagaCandidato(CandidatoBean candidato, SelectItem vaga, EleicaoBean eleicao, CargoBean cargoSelecionado) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				EleicaoBean dados_eleicao = this.getEleicao(eleicao.getId());
				if(dados_eleicao != null) {
					Document documento = new Document();
					Document doc1 = (Document) this.getGambi(eleicao.getId());
					
					for(CargoBean c : dados_eleicao.getCargos()) {
						if(c.getId().equals(cargoSelecionado.getId())) {
							int x = 0;
							ArrayList<String> lista_candidatos = new ArrayList<String>();
							if(cargoSelecionado.getVagaCandidato() != null) {
								for(CandidatoBean ce: cargoSelecionado.getVagaCandidato()) {
									if(ce != null) {
										lista_candidatos.add(x, ce.getId());
									}else {
										lista_candidatos.add(x, null);
									}
									x++;
								}
							
							}else {
								for(x = 0; x < cargoSelecionado.getQuantidade(); x++) {
									lista_candidatos.add(x, null);
								}
							}
							
							lista_candidatos.set((Integer) vaga.getValue(), candidato.getId());
							
							doc1.put(c.getId(), lista_candidatos);
							break;
						}
					}
					
					documento.put("cargos_candidato", doc1);
					colection.updateOne(eq("_id", new ObjectId(dados_eleicao.getId())), new Document("$set", documento));
				}
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
