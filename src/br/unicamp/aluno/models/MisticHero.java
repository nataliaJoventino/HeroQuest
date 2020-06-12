package br.unicamp.aluno.models;

//Os herois misticos podem dominar os 4 elementos e lançar magias
public class MisticHero extends Hero {

	public MisticHero(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	//Joga a magia
	//Para atirar a magia é necessário que o personagem a esteja segurando em uma de suas mãos
	public void throwSpell() {
		//Checando se está na mão direita
		if(this.getRightHand() instanceof Spell) {
			Spell spell = (Spell) this.getRightHand();
			
			//lançando o feitiço
			spell.cast();
			this.holdWithRightHand(null);
			
			return;
		}
		
		//Checando se está na mão esquerda e lançando caso esteja
		if(this.getLeftHand() instanceof Spell) {
			Spell spell = (Spell) this.getLeftHand();
			
			//lançando o feitiço
			spell.cast();
			this.holdWithLeftHand(null);
			return;
		}
		
		//Caso ele não esteja segurando nenhum feitiço
		System.out.println("Você não está segurando nenhum feitiço!");
		return;
		
	}
	
}
