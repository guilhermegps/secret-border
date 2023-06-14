package secretborder.crypto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bech32Prefix {
    NPUB("npub", "public key"),
    NSEC("nsec", "private key");

    private final String code;
    private final String description;
}
