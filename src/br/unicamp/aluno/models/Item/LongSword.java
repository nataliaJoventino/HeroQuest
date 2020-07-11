package br.unicamp.aluno.models.Item;

import br.unicamp.aluno.models.Hero.Hero;

public class LongSword extends Weapon{
	
	//O caso da LongsWord é que é necessário as duas mãos do heroi para que ela seja equipada
	//Também serão acrescentados dados de ataque
	@Override
	public Item equipTheHero(Hero hero) {
		//Segurando com as duas mãos
		hero.holdWithRightHand(this);
		hero.holdWithLeftHand(this);
		//Alterando a quantidade de dados de ataque
		hero.setQuantityOfAttackDices(hero.getQuantityOfAttackDices() + 3);
		return this;
	}

	@Override
	public void unequipTheHero(Hero hero) {
		//Soltando das duas mãos
		hero.holdWithRightHand(null);
		hero.holdWithLeftHand(null);
		//Alterando a quantidade de dados de ataque
		hero.setQuantityOfAttackDices(hero.getQuantityOfAttackDices() - 3);
		hero.storeInBackpack(this);
	}
	
}
