package br.com.hyteck.investiment.services;

import br.com.hyteck.investiment.events.ConvertedObjectsEvent;
import br.com.hyteck.investiment.framework.AbstractService;
import br.com.hyteck.investiment.stocks.models.Wallet;
import br.com.hyteck.investiment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class WalletService extends AbstractService<Wallet, String> {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public WalletService(WalletRepository repository) {
        super(repository);
    }
    @Override
    public Wallet validateSave(Wallet wallet) {
        return wallet;
    }

    @Override
    public WalletRepository getRepository() {
        return (WalletRepository) super.getRepository();
    }

    public Collection<Wallet> findOrSaveAllByNames(Set<String> names) {
        var wallets = getRepository().findDistinctByNameIn(names);
        if(wallets.isEmpty() || wallets.size() < names.size()){
            var walletsForSave = names.stream().map(name-> new Wallet(UUID.randomUUID(), name)).collect(Collectors.toList());
            walletsForSave.removeAll(wallets);
            wallets.addAll(getRepository().saveAllAndFlush(walletsForSave));
        }

        return wallets;
    }

    public void findOrSaveAllByNamesEvent(Set<String> names){
        findOrSaveAllByNames(names);
        applicationEventPublisher.publishEvent(new ConvertedObjectsEvent(this));

    }
}
