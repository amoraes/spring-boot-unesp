package br.unesp.exemplo.entities.enums;

/**
 * Situação da solicitação de um perfil de acesso
 * @author Alessandro Moraes
 */
public enum TamanhoCamiseta {
	
	P {
        @Override
        public String getDescricao() {
            return "P";
        }
    },
	M {
        @Override
        public String getDescricao() {
            return "M";
        }
    },
	G {
        @Override
        public String getDescricao() {
            return "G";
        }
    };
	
	public abstract String getDescricao();

    @Override
    public String toString() {
        return getDescricao();
    }
}
