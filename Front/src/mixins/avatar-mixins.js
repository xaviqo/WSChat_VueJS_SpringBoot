import {Global} from "@/global";

export const avatarMixins = {
    methods: {
        avatarURL: (style,nickname) => {
            return `${Global.avatarUrl}/${style.toLowerCase()}/svg?seed=${nickname}`
        },
        randomizeStyle: (avatars, actualStyle) => {
            const len = avatars.length;
            let newStyle;
            if (len>0){
                do {
                    const rndPos = Math.floor(
                        len * Math.random()
                    );
                    newStyle = avatars[rndPos];
                } while (newStyle === actualStyle);
            }
            return newStyle;
        },
    }
}