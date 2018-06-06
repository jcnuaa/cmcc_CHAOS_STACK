package com.inria.spirals.mgonzale.domain;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.inria.spirals.mgonzale.domain.operation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "failure")
public class FailureMode {

	@Autowired
	private BlockAllNetworkTraffic block;

	@Autowired
	private ShutdownInstance si;

	@Autowired
	private ShOperation shOperation;

	private HashMap<String, Boolean> modes;

	public HashMap<String, Boolean> getModes() {
		return modes;
	}

	public void setModes(HashMap<String, Boolean> modes) {
		this.modes = modes;
	}

	public String pickFailureMode() {

		List<String> keys = modes.entrySet().stream()
				.filter(map -> map.getValue() == true)
				.map(map -> map.getKey())
				.collect(Collectors.toList());

		int random = 0;
		if (keys.size() >= 2) {
			random = ThreadLocalRandom.current().nextInt(0, keys.size());
		} else {
			random = 0;
		}

		String randomKey = keys.get(random);
		return randomKey;

	}

	public void destroy(Member member) throws DestructionException {
		String mode = pickFailureMode();
		switch (mode) {
			case "shutdowninstance":
				si.terminateNow(member);
				break;
			case "blockallnetworktraffic":
				block.blockAllNetworkTraffic(member);
				break;
			case "burncpu":
			case "burnio":
			case "faildns":
			case "filldisk":
			case "killprocesses":
			case "networkcorruption":
			case "networklatency":
			case "networkloss":
			case "nullroute":
				shOperation.apply(member, mode);
				break;
			default:
				break;

		}
	}
}
